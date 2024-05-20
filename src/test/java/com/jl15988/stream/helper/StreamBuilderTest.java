package com.jl15988.stream.helper;

import com.jl15988.stream.helper.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    }
}
