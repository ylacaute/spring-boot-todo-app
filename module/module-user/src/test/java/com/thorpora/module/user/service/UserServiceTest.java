package com.thorpora.module.user.service;

import com.thorpora.module.user.domain.User;
import com.thorpora.module.user.fixture.UserFixtures;
import com.thorpora.module.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserServiceTest {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    private UserRepository userRepository;
    private UserService userService;
    private UserMailService userMailService;

    @Before
    public void beforeMethod() {
        userRepository = Mockito.mock(UserRepository.class);
        userMailService = Mockito.mock(UserMailService.class);
        userService = new UserService(userRepository, userMailService);
    }

    @Test
    public void testSomeMethod() {
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");
        System.out.println("-------------------------------- UNIT TEST ---------------------------------");

        logger.info("Hi");
        //throw new RuntimeException("fuckkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
    }

    @Test
    public void testSomeMethod2() {
        logger.info("Hello");
    }


    @Test
    public void seriaTest() throws IOException, ClassNotFoundException {
        User user = UserFixtures.create();

        String string = toString64(user);

        System.out.println(" Encoded serialized version " );
        System.out.println(string);

        User some = (User) fromString64( string );
        System.out.println( "\n\nReconstituted object");
        System.out.println(some);


    }

       /** Read the object from Base64 string. */
    private static Object fromString64(String value) throws IOException, ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode(value);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object object = ois.readObject();
        ois.close();
        return object;
    }
    /** Write the object to a Base64 string. */
    private static String toString64(Serializable object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }


    public String serializeMapEntry(Map.Entry<String, Serializable> entry) {
        String value;
        try {
            value = toString64(entry.getValue());
        } catch (IOException e) {
            value = null;
        }
        return entry.getKey() + "=" + value;
    }

    @Test
    public void convertToDatabaseColumn() throws IOException, ClassNotFoundException {
        Map<String, Serializable> attribute = new HashMap<>();
        User user = UserFixtures.create();
        attribute.put("k1", "v1");
        attribute.put("k2", "v2");
        attribute.put("k3", user);

        String result = attribute.entrySet().stream()
                .map(this::serializeMapEntry)
                .collect(Collectors.joining(";"));

        System.out.println("JOIN : " + result);

        String[] mapEntries = result.split(";");

        String userEntry = mapEntries[2];
        String key = userEntry.split("=")[0];
        String value = userEntry.split("=")[1];

        System.out.println(">> " + value);
        Object obj = fromString64(value);

        System.out.println(">> final obj" + obj);
    }

}