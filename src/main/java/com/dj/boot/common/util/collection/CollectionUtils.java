package com.dj.boot.common.util.collection;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * CollectionUtils
 * 
 * @author: Aldwin Su
 * @version: 2019-06-22 10:29:36
 */
public class CollectionUtils {
	/**
	 * If collection does null or empty?
	 * @param collection Elements collection.
	 * @return boolean
	 */
	public static <E> boolean isNullOrEmpty(Collection<E> collection) {
		return collection == null || collection.size() == 0;
	}

	/**
	 * If collection does not empty?
	 * @param collection Elements collection.
	 * @return boolean
	 */
	public static <E> boolean isNotEmpty(Collection<E> collection) {
		return collection != null && collection.size() > 0;
	}

	/**
	 * Sort by comparator and return the minimum value.
	 * @param collection Elements collection.
	 * @param comparator Comparator for sort.
	 * @return {@code E}
	 */
	public static <E> E min(Collection<E> collection, Comparator<? super E> comparator) {
		Optional<E> optional = collection.stream().min(comparator);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;
	}

	/**
	 * Return the minimum integer value.
	 * @param collection Elements collection.
	 * @return int
	 */
	public static int minInt(Collection<Integer> collection) {
		Integer value = min(collection, Comparator.comparingInt(i -> i));

		if (value == null) {
			return 0;
		}

		return value;
	}

	/**
	 * Sort by comparator and return the maximum value.
	 * @param collection Elements collection.
	 * @param comparator Comparator for sort.
	 * @return {@code E}
	 */
	public static <E> E max(Collection<E> collection, Comparator<? super E> comparator) {
		Optional<E> optional = collection.stream().max(comparator);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;
	}

	/**
	 * Return the maximum integer value.
	 * @param collection Elements collection.
	 * @return int
	 */
	public static int maxInt(Collection<Integer> collection) {
		Integer value = max(collection, Comparator.comparingInt(i -> i));

		if (value == null) {
			return 0;
		}

		return value;
	}

	/**
	 * Calculate elements that meet predicate command.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @return long
	 */
	public static <E> long count(Collection<E> collection, Predicate<E> predicate) {
		return collection.stream().filter(predicate).count();
	}

	/**
	 * Execute predicate and check whether any object exist?
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @return boolean
	 */
	public static <E> boolean exist(Collection<E> collection, Predicate<E> predicate) {
		return count(collection, predicate) > 0;
	}

	/**
	 * Find element by predicate command and return optional object.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @return {@code Optional<E>}
	 */
	public static <E> Optional<E> findOptional(Collection<E> collection, Predicate<E> predicate) {
		return collection.stream().filter(predicate).findAny();
	}

	/**
	 * Find element by predicate command and return it.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @return {@code E}
	 */
	public static <E> E find(Collection<E> collection, Predicate<E> predicate) {
		Optional<E> optional = findOptional(collection, predicate);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;
	}

	/**
	 * Parallel find element by predicate command and return optional object.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @return {@code Optional<E>}
	 */
	public static <E> Optional<E> parallelFindOptional(Collection<E> collection, Predicate<E> predicate) {
		return collection.stream().parallel().filter(predicate).findAny();
	}

	/**
	 * Parallel find element by predicate command and return it.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @return {@code E}
	 */
	public static <E> E parallelFind(Collection<E> collection, Predicate<E> predicate) {
		Optional<E> optional = parallelFindOptional(collection, predicate);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;
	}

	/**
	 * Query elements by predicate command and return a stream.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @return {@code Stream<E>}
	 */
	public static <E> Stream<E> query(Collection<E> collection, Predicate<E> predicate) {
		return collection.stream().filter(predicate);
	}

	/**
	 * Parallel query elements by predicate command and return a stream.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @return {@code Stream<E>}
	 */
	public static <E> Stream<E> parallelQuery(Collection<E> collection, Predicate<E> predicate) {
		return collection.stream().parallel().filter(predicate);
	}

	/**
	 * Query elements by predicate command and return a list.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @return {@code List<E>}
	 */
	public static <E> List<E> list(Collection<E> collection, Predicate<E> predicate) {
		return query(collection, predicate).collect(Collectors.toList());
	}

	/**
	 * Parallel query elements by predicate command and return a list.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @return {@code List<E>}
	 */
	public static <E> List<E> parallelList(Collection<E> collection, Predicate<E> predicate) {
		return parallelQuery(collection, predicate).collect(Collectors.toList());
	}

	/**
	 * Map some fields and return a stream.
	 * @param collection Elements collection.
	 * @param mapper Fields mapper.
	 * @return {@code Stream<R>}
	 */
	public static <E, R> Stream<R> map(Collection<E> collection, Function<? super E, ? extends R> mapper) {
		return collection.stream().map(mapper);
	}

	/**
	 * Map some fields and return a list.
	 * @param collection Elements collection.
	 * @param mapper Fields mapper.
	 * @return {@code List<R>}
	 */
	public static <E, R> List<R> mapToList(Collection<E> collection, Function<? super E, ? extends R> mapper) {
		return map(collection, mapper).collect(Collectors.toList());
	}

	/**
	 * Map some fields and return a Set.
	 * @param collection Elements collection.
	 * @param mapper Fields mapper.
	 * @return {@code Set<R>}
	 */
	public static <E, R> Set<R> mapToSet(Collection<E> collection, Function<? super E, ? extends R> mapper) {
		return map(collection, mapper).collect(Collectors.toSet());
	}

	/**
	 * Query elements by predicate command, map some fields and return a stream.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param mapper Fields mapper.
	 * @return {@code Stream<R>}
	 */
	public static <E, R> Stream<R> map(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends R> mapper) {
		return query(collection, predicate).map(mapper);
	}

	/**
	 * Query elements by predicate command, map some fields and return a list.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param mapper Fields mapper.
	 * @return {@code List<R>}
	 */
	public static <E, R> List<R> mapToList(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends R> mapper) {
		return map(collection, predicate, mapper).collect(Collectors.toList());
	}

	/**
	 * Query elements by predicate command, map some fields and return a Set.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param mapper Fields mapper.
	 * @return {@code Set<R>}
	 */
	public static <E, R> Set<R> mapToSet(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends R> mapper) {
		return map(collection, predicate, mapper).collect(Collectors.toSet());
	}

	/**
	 * Parallel map some fields and return a stream.
	 * @param collection Elements collection.
	 * @param mapper Fields mapper.
	 * @return {@code Stream<R>}
	 */
	public static <E, R> Stream<R> parallelMap(Collection<E> collection, Function<? super E, ? extends R> mapper) {
		return collection.stream().parallel().map(mapper);
	}

	/**
	 * Parallel map some fields and return a list.
	 * @param collection Elements collection.
	 * @param mapper Fields mapper.
	 * @return {@code List<R>}
	 */
	public static <E, R> List<R> parallelMapToList(Collection<E> collection, Function<? super E, ? extends R> mapper) {
		return parallelMap(collection, mapper).collect(Collectors.toList());
	}

	/**
	 * Parallel query elements by predicate command, map some fields and return a stream.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param mapper Fields mapper.
	 * @return {@code Stream<R>}
	 */
	public static <E, R> Stream<R> parallelMap(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends R> mapper) {
		return collection.stream().parallel().filter(predicate).map(mapper);
	}

	/**
	 * Parallel query elements by predicate command, map some fields and return a list.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param mapper Fields mapper.
	 * @return {@code List<R>}
	 */
	public static <E, R> List<R> parallelMapToList(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends R> mapper) {
		return parallelMap(collection, predicate, mapper).collect(Collectors.toList());
	}

	/**
	 * Map some fields, remove repeated items and return a stream.
	 * @param collection Elements collection.
	 * @param mapper Fields mapper.
	 * @return {@code Stream<? extends R>}
	 */
	public static <E, R> Stream<? extends R> mapAndDistinct(Collection<E> collection, Function<? super E, ? extends R> mapper) {
		return map(collection, mapper).distinct();
	}

	/**
	 * Map some fields, remove repeated items and return a list.
	 * @param collection Elements collection.
	 * @param mapper Fields mapper.
	 * @return {@code List<R>}
	 */
	public static <E, R> List<R> mapAndDistinctToList(Collection<E> collection, Function<? super E, ? extends R> mapper) {
		return mapAndDistinct(collection, mapper).collect(Collectors.toList());
	}

	/**
	 * Map some fields, remove repeated items and return a set.
	 * @param collection Elements collection.
	 * @param mapper Fields mapper.
	 * @return {@code Set<R>}
	 */
	public static <E, R> Set<R> mapAndDistinctToSet(Collection<E> collection, Function<? super E, ? extends R> mapper) {
		return mapAndDistinct(collection, mapper).collect(Collectors.toSet());
	}

	/**
	 * Query elements by predicate command, map some fields, remove repeated items and return a stream.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param mapper Fields mapper.
	 * @return {@code Stream<? extends R>}
	 */
	public static <E, R> Stream<? extends R> mapAndDistinct(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends R> mapper) {
		return map(collection, predicate, mapper).distinct();
	}

	/**
	 * Query elements by predicate command, map some fields, remove repeated items and return a list.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param mapper Fields mapper.
	 * @return {@code List<R>}
	 */
	public static <E, R> List<R> mapAndDistinctToList(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends R> mapper) {
		return mapAndDistinct(collection, predicate, mapper).collect(Collectors.toList());
	}

	/**
	 * Query elements by predicate command, map some fields, remove repeated items and return a set.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param mapper Fields mapper.
	 * @return {@code Set<R>}
	 */
	public static <E, R> Set<R> mapAndDistinctToSet(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends R> mapper) {
		return mapAndDistinct(collection, predicate, mapper).collect(Collectors.toSet());
	}

	/**
	 * Map some fields (for each item, one field has an collection of values) and return a list.
	 * @param collection Elements collection.
	 * @param mapper Fields mapper.
	 * @return {@code List<R>}
	 */
	public static <E, R> List<R> mapManyToList(Collection<E> collection, Function<? super E, ? extends Stream<? extends R>> mapper) {
		List<R> list = new LinkedList<>();

		map(collection, mapper).forEach(i -> {
			list.addAll(i.collect(Collectors.toList()));
		});

		return list;
	}

	/**
	 * Group collection by classifier to map and return it.
	 * @param collection Elements collection.
	 * @param classifier Classifier for group.
	 * @return {@code Map<K,List<E>>}
	 */
	public static <K, E> Map<K, List<E>> groupToMap(Collection<E> collection, Function<? super E, ? extends K> classifier) {
		return collection.stream().collect(Collectors.groupingBy(classifier));
	}

	/**
	 * Parallel group collection by classifier to map and return it.
	 * @param collection Elements collection.
	 * @param classifier Classifier for group.
	 * @return {@code Map<K,List<E>>}
	 */
	public static <K, E> Map<K, List<E>> parallelGroupToMap(Collection<E> collection, Function<? super E, ? extends K> classifier) {
		return collection.stream().parallel().collect(Collectors.groupingBy(classifier));
	}

	/**
	 * Query elements by predicate command, group collection by classifier to map and return it.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param classifier Classifier for group.
	 * @return {@code Map<K,List<E>>}
	 */
	public static <K, E> Map<K, List<E>> groupToMap(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends K> classifier) {
		return query(collection, predicate).collect(Collectors.groupingBy(classifier));
	}

	/**
	 * Parallel group elements by predicate command, group collection by classifier to map and return it.
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param classifier Classifier for group.
	 * @return {@code Map<K,List<E>>}
	 */
	public static <K, E> Map<K, List<E>> parallelGroupToMap(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends K> classifier) {
		return parallelQuery(collection, predicate).collect(Collectors.groupingBy(classifier));
	}

	/**
	 * Convert collection to Map.
	 * @param <E> the type of the input elements
	 * @param <K> the output type of the key mapping function
	 * @param <V> the output type of the value mapping function
	 * @param collection Elements collection.
	 * @param keyMapper a mapping function to produce keys.
	 * @param valueMapper a mapping function to produce values.
	 * @return {@code Map<K,V>}
	 */
	public static <E, K, V> Map<K, V> toMap(Collection<E> collection, Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper) {
		return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper));
	}

	/**
	 * Convert collection to Map.
	 * @param <E> the type of the input elements
	 * @param <K> the output type of the key mapping function
	 * @param <V> the output type of the value mapping function
	 * @param collection Elements collection.
	 * @param keyMapper a mapping function to produce keys.
	 * @param valueMapper a mapping function to produce values.
	 * @param mergeFunction a merge function, selector function.
	 * @param mapSupplier a function which returns a new, empty Map into which the results will be inserted
	 * @return {@code Map<K,V>}
	 */
	public static <E, K, V, M extends Map<K, V>> Map<K, V> toMap(Collection<E> collection, Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper, BinaryOperator<V> mergeFunction, Supplier<M> mapSupplier) {
		return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapSupplier));
	}

	/**
	 * Parallel convert collection to Map.
	 * @param <E> the type of the input elements
	 * @param <K> the output type of the key mapping function
	 * @param <V> the output type of the value mapping function
	 * @param collection Elements collection.
	 * @param keyMapper a mapping function to produce keys.
	 * @param valueMapper a mapping function to produce values.
	 * @return {@code Map<K,V>}
	 */
	public static <E, K, V> Map<K, V> parallelToMap(Collection<E> collection, Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper) {
		return collection.stream().parallel().collect(Collectors.toMap(keyMapper, valueMapper));
	}

	/**
	 * Parallel convert collection to Map.
	 * @param <E> the type of the input elements
	 * @param <K> the output type of the key mapping function
	 * @param <V> the output type of the value mapping function
	 * @param collection Elements collection.
	 * @param keyMapper a mapping function to produce keys.
	 * @param valueMapper a mapping function to produce values.
	 * @param mergeFunction a merge function, selector function.
	 * @return {@code Map<K,V>}
	 */
	public static <E, K, V> Map<K, V> parallelToMap(Collection<E> collection, Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper, BinaryOperator<V> mergeFunction) {
		return collection.stream().parallel().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
	}

	/**
	 * Convert collection to Map.
	 * @param <E> the type of the input elements
	 * @param <K> the output type of the key mapping function
	 * @param <V> the output type of the value mapping function
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param keyMapper a mapping function to produce keys.
	 * @param valueMapper a mapping function to produce values.
	 * @return {@code Map<K,V>}
	 */
	public static <E, K, V> Map<K, V> toMap(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper) {
		return query(collection, predicate).collect(Collectors.toMap(keyMapper, valueMapper));
	}

	/**
	 * Convert collection to Map.
	 * @param <E> the type of the input elements
	 * @param <K> the output type of the key mapping function
	 * @param <V> the output type of the value mapping function
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param keyMapper a mapping function to produce keys.
	 * @param valueMapper a mapping function to produce values.
	 * @param mergeFunction a merge function, selector function.
	 * @return {@code Map<K,V>}
	 */
	public static <E, K, V> Map<K, V> toMap(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper, BinaryOperator<V> mergeFunction) {
		return query(collection, predicate).collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
	}

	/**
	 * Parallel convert collection to Map.
	 * @param <E> the type of the input elements
	 * @param <K> the output type of the key mapping function
	 * @param <V> the output type of the value mapping function
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param keyMapper a mapping function to produce keys.
	 * @param valueMapper a mapping function to produce values.
	 * @return {@code Map<K,V>}
	 */
	public static <E, K, V> Map<K, V> parallelToMap(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper) {
		return parallelQuery(collection, predicate).collect(Collectors.toMap(keyMapper, valueMapper));
	}

	/**
	 * Parallel convert collection to Map.
	 * @param <E> the type of the input elements
	 * @param <K> the output type of the key mapping function
	 * @param <V> the output type of the value mapping function
	 * @param collection Elements collection.
	 * @param predicate Predicate command.
	 * @param keyMapper a mapping function to produce keys.
	 * @param valueMapper a mapping function to produce values.
	 * @param mergeFunction a merge function, selector function.
	 * @return {@code Map<K,V>}
	 */
	public static <E, K, V> Map<K, V> parallelToMap(Collection<E> collection, Predicate<E> predicate, Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper, BinaryOperator<V> mergeFunction) {
		return parallelQuery(collection, predicate).collect(Collectors.toMap(keyMapper, valueMapper));
	}

	/**
	 * Paging collection with specified page index and page size.
	 * @param collection Elements collection.
	 * @param pageIndex Page index.
	 * @param pageSize Page size.
	 * @return {@code List<E>}
	 */
	public static <E> List<E> paging(Collection<E> collection, int pageIndex, int pageSize) {
		if (isNullOrEmpty(collection) || pageIndex < 1) {
			return new ArrayList<>();
		}

		int skip = (pageIndex - 1) * pageSize;

		if (skip < 0 || skip >= collection.size()) {
			return new ArrayList<>();
		}

		return collection.stream().skip(skip).limit(pageSize).collect(Collectors.toList());
	}

	/**
	 * Get the toppest count elements.
	 * @param collection Elements collection.
	 * @param count Count number.
	 * @return {@code List<E>}
	 */
	public static <E> List<E> top(Collection<E> collection, int count) {
		if (isNullOrEmpty(collection) || count < 1) {
			return new ArrayList<>();
		}

		return collection.stream().limit(count).collect(Collectors.toList());
	}
}