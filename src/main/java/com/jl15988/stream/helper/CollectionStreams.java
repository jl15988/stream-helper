package com.jl15988.stream.helper;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 集合流工具
 *
 * @author Jalon
 * @since 2024/5/20 14:05
 **/
public class CollectionStreams {

    /**
     * List 转 Map
     * <p>
     * <b>注：需保证 key 值不会重复，如果无法保证可使用 toMapUnique 方法来自动去重</b>
     * </p>
     *
     * @param list        List 集合
     * @param keyMapper   键映射函数
     * @param valueMapper 值映射函数
     * @param <T>         List 元素类型
     * @param <K>         返回结果 Map 的键类型
     * @param <V>         返回结果 Map 的值类型
     * @return Map
     */
    public static <T, K, V> Map<K, V> toMap(Collection<T> list, Function<? super T, K> keyMapper, Function<? super T, V> valueMapper) {
        return list.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

    /**
     * 通过 key 来转换 Map，则值为对应的 List 元素
     * <p>
     * <b>注：需保证 key 值不会重复，如果无法保证可使用 toMapWithKeyUnique 方法来自动去重</b>
     * </p>
     *
     * @param list      List 集合
     * @param keyMapper 键映射函数
     * @param <T>       List 元素类型
     * @param <K>       返回结果 Map 的键类型
     * @return Map
     */
    public static <T, K> Map<K, T> toMapWithKey(Collection<T> list, Function<? super T, K> keyMapper) {
        return CollectionStreams.toMap(list, keyMapper, v -> v);
    }

    /**
     * 通过 key 来转换 Map 且对于 key 相同的做去重处理，去重取的是第一个元素，如果要取最后一个元素可使用 toMapWithKeyUnique2 方法
     *
     * @param list         List 集合
     * @param keyMapper    键映射函数
     * @param defaultValue 当值为空时的默认值
     * @param <T>          List 元素类型
     * @param <K>          返回结果 Map 的键类型
     * @return Map
     */
    public static <T, K> Map<K, T> toMapWithKeyUnique(Collection<T> list, Function<? super T, K> keyMapper, T defaultValue) {
        return list.stream().collect(Collectors.toMap(keyMapper, v -> Optional.ofNullable(v).orElse(defaultValue), (v, v2) -> v));
    }

    /**
     * 通过 key 来转换 Map 且对于 key 相同的做去重处理，去重取的是最后一个元素，如果要取第一个元素可使用 toMapWithKeyUnique 方法
     *
     * @param list         List 集合
     * @param keyMapper    键映射函数
     * @param defaultValue 当值为空时的默认值
     * @param <T>          List 元素类型
     * @param <K>          返回结果 Map 的键类型
     * @return Map
     */
    public static <T, K> Map<K, T> toMapWithKeyUnique2(Collection<T> list, Function<? super T, K> keyMapper, T defaultValue) {
        return list.stream().collect(Collectors.toMap(keyMapper, v -> Optional.ofNullable(v).orElse(defaultValue), (v, v2) -> v2));
    }

    /**
     * List 转 Map，会对相同 key 的元素做去重处理，取的为第一个元素，如果要取最后一个元素可使用 toMapUnique2 方法
     *
     * @param list        List 集合
     * @param keyMapper   键映射函数
     * @param valueMapper 值映射函数
     * @param <T>         List 元素类型
     * @param <K>         返回结果 Map 的键类型
     * @param <V>         返回结果 Map 的值类型
     * @return Map
     */
    public static <T, K, V> Map<K, V> toMapUnique(Collection<T> list, Function<? super T, K> keyMapper, Function<? super T, V> valueMapper) {
        return list.stream().collect(Collectors.toMap(keyMapper, valueMapper, (v, v2) -> v));
    }

    /**
     * List 转 Map，会对相同 key 的元素做去重处理，取的为最后一个元素，如果要取第一个元素可使用 toMapUnique 方法
     *
     * @param list        List 集合
     * @param keyMapper   键映射函数
     * @param valueMapper 值映射函数
     * @param <T>         List 元素类型
     * @param <K>         返回结果 Map 的键类型
     * @param <V>         返回结果 Map 的值类型
     * @return Map
     */
    public static <T, K, V> Map<K, V> toMapUnique2(Collection<T> list, Function<? super T, K> keyMapper, Function<? super T, V> valueMapper) {
        return list.stream().collect(Collectors.toMap(keyMapper, valueMapper, (v, v2) -> v2));
    }

    /**
     * 按某个字段进行分组
     *
     * @param list       List 集合
     * @param classifier 分组处理
     * @param <T>        List 元素类型
     * @param <K>        返回结果 Map 的值类型
     * @return Map
     */
    public static <T, K> Map<K, List<T>> groupBy(Collection<T> list, Function<? super T, K> classifier) {
        return list.stream().collect(Collectors.groupingBy(classifier));
    }

    /**
     * 获取 List 元素属性值转数组
     *
     * @param list   List 集合
     * @param mapper 映射器
     * @param clazz  属性类型
     * @param <T>    List 元素类型
     * @param <V>    返回数组元素类型
     * @return Array
     */
    @SuppressWarnings("unchecked")
    public static <T, V> V[] toArray(Collection<T> list, Function<? super T, V> mapper, Class<V> clazz) {
        return list.stream().map(mapper).toArray(len -> (V[]) Array.newInstance(clazz, len));
    }

    /**
     * 拼接 List 为字符串
     *
     * @param list      List 集合
     * @param mapper    映射器
     * @param delimiter 分隔符
     * @param <T>       List 元素类型
     * @param <V>       映射器返回的类型
     * @return 拼接好的字符串
     */
    public static <T, V> String join(Collection<T> list, Function<? super T, V> mapper, CharSequence delimiter) {
        return list.stream().map(v -> (String) mapper.apply(v)).collect(Collectors.joining(delimiter));
    }

    /**
     * List 元素类型转换
     *
     * @param list   List 集合
     * @param mapper 转换器
     * @param <T>    List 元素类型
     * @param <R>    转换后的元素类型
     * @return 转换后的 List
     */
    public static <T, R> List<R> convert(Collection<T> list, Function<T, R> mapper) {
        if (list == null) {
            return null;
        }
        return list.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * List 元素转为 String 类型,实际上调用的为元素内部的 toString 方法
     *
     * @param list List 集合
     * @param <T>  List 元素类型
     * @return 转换后的 List
     */
    public static <T> List<String> convertString(Collection<T> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(Object::toString).collect(Collectors.toList());
    }

    /**
     * 判断 List 中的某个属性是否有目标值
     *
     * @param list        List 集合
     * @param valueMapper 属性处理器
     * @param target      目标值
     * @param <T>         List 元素类型
     * @param <V>         目标值类型
     */
    public static <T, V> boolean matchValue(Collection<T> list, Function<T, V> valueMapper, V target) {
        return list.stream().anyMatch(t -> {
            V value = valueMapper.apply(t);
            if (value == null) {
                return target == null;
            } else {
                return value.equals(target);
            }
        });
    }

    public static <T, V> boolean anyMatch(Collection<T> list, Predicate<T> predicate) {
        return list.stream().anyMatch(predicate);
    }

    public static <T> List<T> update(Collection<T> list, Consumer<T> action) {
        if (list == null) return null;
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().peek(action).collect(Collectors.toList());
    }

    /**
     * 根据字段去重
     *
     * @param list         List 集合
     * @param keyMapper    字段处理器
     * @param defaultValue 内容为空时的默认值
     * @param <T>          List 元素类型
     * @param <K>          字段对应值的类型
     * @return 去重后的 List
     */
    public static <T, K> List<T> uniqueWithKey(Collection<T> list, Function<T, K> keyMapper, T defaultValue) {
        return list.stream().collect(Collectors.collectingAndThen(
                Collectors.toMap(keyMapper, v -> Optional.ofNullable(v).orElse(defaultValue), (v, v2) -> v),
                kvMap -> new ArrayList<>(kvMap.values())
        ));
    }

    /**
     * 根据字段正向排序
     *
     * @param list         List 集合
     * @param keyExtractor 字段处理器
     * @param <T>          List 元素类型
     * @param <K>          字段对应值的类型
     * @return 排序后的 List
     */
    public static <T, K extends Comparable<? super K>> List<T> orderByAsc(Collection<T> list, Function<T, K> keyExtractor) {
        return list.stream().sorted(Comparator.comparing(keyExtractor)).collect(Collectors.toList());
    }

    /**
     * 根据字段反向排序
     *
     * @param list         List 集合
     * @param keyExtractor 字段处理器
     * @param <T>          List 元素类型
     * @param <K>          字段对应值的类型
     * @return 排序后的 List
     */
    public static <T, K extends Comparable<? super K>> List<T> orderByDesc(Collection<T> list, Function<T, K> keyExtractor) {
        return list.stream().sorted(Comparator.comparing(keyExtractor).reversed()).collect(Collectors.toList());
    }
}
