package com.jl15988.stream.helper;

import com.jl15988.stream.helper.domain.Student;
import com.jl15988.stream.helper.domain.User;

import java.util.*;

/**
 * @author Jalon
 * @since 2024/5/20 13:54
 **/
public class StreamBuilderTest {

    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        User user1 = new User("1", 20);
        User user2 = new User("2", 23);
        User user3 = new User("3", 22);
        User user4 = new User("4", 30);
        User user5 = new User("1", 50);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.add(user5);
        StreamBuilder<User> userStreamBuilder = new StreamBuilder<>(list);
        Map<String, Integer> stringUserMap = userStreamBuilder.toMap(User::getName, User::getAge);
        System.out.println(stringUserMap);

        Map<String, User> stringUserMap1 = CollectionStreams.toMapUnique(list, User::getName, user -> user);
//        stringUserMap1.merge("1", 2, (user, user21) -> )

        Map<String, List<User>> stringListMap = CollectionStreams.groupBy(list, User::getName);
        System.out.println(stringListMap);
        Map<String, User> stringUserMap2 = CollectionStreams.toMapWithKeyUnique(list, User::getName, new User());
        System.out.println(stringUserMap2);

        System.out.println("----------------------");
        Integer[] integers = CollectionStreams.toArray(list, User::getAge, Integer.class);
        System.out.println(Arrays.toString(integers));

        String join = CollectionStreams.join(list, User::getName, "#");
        System.out.println(join);

        Collection<String> convert = CollectionStreams.convert(list, User::getName);
        System.out.println(convert);

        boolean match = CollectionStreams.matchValue(list, User::getName, "小明");
        boolean match2 = CollectionStreams.matchValue(list, User::getName, "1");
        System.out.println(match);
        System.out.println(match2);


//        List<Integer> unique = CollectionStreams.unique(list, User::getName, User::getAge, 2);
//        System.out.println(unique);
        List<User> users = CollectionStreams.uniqueWithKey(list, User::getName, new User());
        System.out.println(users);


        List<User> users1 = CollectionStreams.orderByAsc(list, User::getAge);
        List<User> users2 = CollectionStreams.orderByDesc(list, User::getAge);
        System.out.println(CollectionStreams.convertString(users1));
        System.out.println(CollectionStreams.convertString(users2));


        System.out.println(CollectionStreams.toMap(null, o -> o, o -> o));
    }
}
