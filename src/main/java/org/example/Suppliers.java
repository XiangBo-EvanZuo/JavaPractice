package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Suppliers {

    // 创建并填充一个集合
    public static <T, C extends Collection<T>> C create(Supplier<C> factory, Supplier<T> gen, int n) {
        return Stream.generate(gen)
                .limit(n)
                .collect(factory, C::add, C::addAll);
    }

    // 填充已有集合
    public static <T, C extends Collection<T>> C fill(C coll, Supplier<T> gen, int n) {
        Stream.generate(gen)
                .limit(n)
                .forEach(coll::add);
        return coll;
    }

    // 使用未绑定的方法引用生成更通用的方法
    public static <T, H extends Collection<T>> H fill(BiConsumer<H, T> func, H coll, Supplier<T> gen, int n) {
        Stream.generate(gen)
                .limit(n)
                .forEach(item -> func.accept(coll, item));
        return coll;
    }
    public static Integer applyInteger() {
        return 1;
    }
    public static Supplier<Integer> applyInteger2 = () -> 1;
    public static void main(String[] args) {
        List<Integer> resList = Suppliers.fill(
                List::add,
                new ArrayList<>(),
                Suppliers.applyInteger2,
                1
        );
        System.out.println(resList);
        List<String> resList2 = Suppliers.create(
                ArrayList::new,
                String::new,
                2
        );
        System.out.println(resList2);
    }
}
