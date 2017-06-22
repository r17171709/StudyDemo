package com.example.renyu.frescodemo.libs

import android.content.Context
import android.graphics.Bitmap
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.internal.Supplier
import com.facebook.common.memory.MemoryTrimType
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry
import com.facebook.common.util.ByteConstants
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.core.ImagePipelineFactory
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig
import com.facebook.imagepipeline.image.ImmutableQualityInfo
import com.facebook.imagepipeline.image.QualityInfo

/**
 * Created by Administrator on 2017/5/31.
 */
class ImagePipelineConfigUtils {
    companion object {
        // 最大可用内存
        val maxHeapSize: Long = Runtime.getRuntime().maxMemory()
        // 缓存Disk小图片文件夹名称
        val smallDiskCacheName: String = "smallDiskCacheConfig"
        // 缓存Disk普通图片文件夹名称
        val normalDiskCacheName: String = "normalDiskCacheConfig"
        // 缓存Disk文件夹大小
        val diskCacheSize: Int = 100 * ByteConstants.MB
        // 低硬盘空间下缓存Disk文件夹大小
        val lowDiskCacheSize: Int = 50 * ByteConstants.MB
        // 非常低硬盘空间下缓存Disk文件夹大小
        val veryLowDiskCacheSize: Int = 20 * ByteConstants.MB

        fun getDefaultImagePipelineConfig(context: Context) : ImagePipelineConfig {
            val memmoryCacheParams = MemoryCacheParams(
                    maxHeapSize.toInt()/4, // 可用最大内存数，以字节为单位
                    Int.MAX_VALUE, // 内存中允许的最多图片数量
                    maxHeapSize.toInt()/4, // 内存中准备清理但是尚未删除的总图片所可用的最大内存数，以字节为单位
                    Int.MAX_VALUE, // 内存中准备清除的图片最大数量
                    Int.MAX_VALUE) // 内存中单图片的最大大小

            // 缓存策略
            val supplierMemoryCacheParams: Supplier<MemoryCacheParams> = Supplier { return@Supplier memmoryCacheParams }

            // 小图片缓存Disk配置
            val smallDiskCacheConfig = DiskCacheConfig.newBuilder(context)
                    .setBaseDirectoryPath(context.applicationContext.cacheDir) // 设置缓存图片本地根目录路径
                    .setBaseDirectoryName(smallDiskCacheName) // 设置缓存Disk文件夹名称
                    .setMaxCacheSize(diskCacheSize.toLong()) // 设置缓存Disk文件夹大小
                    .setMaxCacheSizeOnLowDiskSpace(lowDiskCacheSize.toLong()) // 设置低硬盘空间下缓存Disk文件夹大小
                    .setMaxCacheSizeOnVeryLowDiskSpace(veryLowDiskCacheSize.toLong()) // 设置非常低硬盘空间下缓存Disk文件夹大小
                    .build()

            // 普通图片缓存Disk配置
            val normalDiskCacheConfig = DiskCacheConfig.newBuilder(context)
                    .setBaseDirectoryPath(context.applicationContext.cacheDir)
                    .setBaseDirectoryName(normalDiskCacheName)
                    .setMaxCacheSize(diskCacheSize.toLong())
                    .setMaxCacheSizeOnLowDiskSpace(lowDiskCacheSize.toLong())
                    .setMaxCacheSizeOnVeryLowDiskSpace(veryLowDiskCacheSize.toLong())
                    .build()

//            OkHttpImagePipelineConfigFactory.newBuilder(context, OkHttpClient())
            var imagePipelineConfig: ImagePipelineConfig.Builder = ImagePipelineConfig.newBuilder(context)
                    .setProgressiveJpegConfig(SimpleProgressiveJpegConfig()) // 渐进式配置
                    .setBitmapsConfig(Bitmap.Config.RGB_565) // 没有透明图片显示要求，设置为RGB_565，减少内存开销
                    .setBitmapMemoryCacheParamsSupplier(supplierMemoryCacheParams) // 配置缓存策略
                    .setSmallImageDiskCacheConfig(smallDiskCacheConfig) // 小图片Disk缓存策略
                    .setMainDiskCacheConfig(normalDiskCacheConfig) // 基本图片Disk缓存策略
                    .setMemoryTrimmableRegistry(NoOpMemoryTrimmableRegistry.getInstance()) // 注册内存调用器，在需要回收内存的时候进行回收
                    .setResizeAndRotateEnabledForNetwork(true) // 对网络图片进行resize处理，减少内存消耗
                    .setDownsampleEnabled(true) // 必须和ImageRequest的ResizeOptions一起使用，作用就是在图片解码时根据ResizeOptions所设的宽高的像素进行解码，这样解码出来可以得到一个更小的Bitmap。
            // ResizeOptions和DownsampleEnabled参数都不影响原图片的大小，影响的是EncodeImage的大小，进而影响Decode出来的Bitmap的大小。
            // ResizeOptions须和此参数结合使用，因为单独使用ResizeOptions的话只支持JPEG图，所以需支持png、jpg、webp需要先设置此参数。

            NoOpMemoryTrimmableRegistry.getInstance().registerMemoryTrimmable {
                trimType ->
                val suggestedTrimRatio: Double = trimType.suggestedTrimRatio
                if (MemoryTrimType.OnCloseToDalvikHeapLimit.suggestedTrimRatio == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.suggestedTrimRatio == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.suggestedTrimRatio == suggestedTrimRatio) {
                    ImagePipelineFactory.getInstance().imagePipeline.clearMemoryCaches()
                }
            }

            return imagePipelineConfig.build()
        }
    }
}

/**
 * 渐进式实现，只针对网路jpg
 */
class ProgressiveJpegConfigClass : ProgressiveJpegConfig {
    override fun getNextScanNumberToDecode(p0: Int): Int {
        return p0+2
    }

    override fun getQualityInfo(p0: Int): QualityInfo {
        val isGoodEnough = p0 >= 5
        return ImmutableQualityInfo.of(p0, isGoodEnough, false)
    }
}