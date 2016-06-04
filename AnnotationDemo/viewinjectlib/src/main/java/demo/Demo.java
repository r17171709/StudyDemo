package demo;

import com.example.annotation.Finder;
import com.example.annotation.InjectInterface;

/**
 * Created by RG on 2016/6/2.
 */
public class Demo<T> implements InjectInterface<T> {

    @Override
    public void inject(Finder finder, T target) {
        finder.setContentView(target, 0);
    }
}
