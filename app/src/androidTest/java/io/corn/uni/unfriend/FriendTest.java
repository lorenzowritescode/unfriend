package io.corn.uni.unfriend;

import android.test.InstrumentationTestCase;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public class FriendTest extends InstrumentationTestCase {

    Date aJoin = new Date(TimeUnit.MILLISECONDS.convert(364, TimeUnit.DAYS));
    Date aFriend = new Date(TimeUnit.MILLISECONDS.convert(60, TimeUnit.DAYS));
    Date aPost = new Date(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
    Date sJoin = new Date(TimeUnit.MILLISECONDS.convert(20, TimeUnit.DAYS));
    Date sFriend = new Date(TimeUnit.MILLISECONDS.convert(8, TimeUnit.DAYS));
    Date sPost = new Date(TimeUnit.MILLISECONDS.convert(19, TimeUnit.DAYS));

    Friend alan = new Friend(1, "Alan", aJoin, aFriend, aPost, 17, 68, 20);
    Friend steve = new Friend(2, "Steve", sJoin, sFriend, sPost, 2, 1, 71);

    public void test() {
        TestResult aRes = alan.testFriend();
        TestResult sRes = steve.testFriend();
        System.out.println("Alan's fakeness: " + aRes.realnessScore );
        System.out.println("Steve's fakeness: " + sRes.realnessScore);
        assert(!aRes.fake);
        assert(sRes.fake);
    }



}