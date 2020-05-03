package com.demo.alltests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.demo.db.entity.BookingTest;
import com.demo.web.utils.EncodeUtilTest;
import com.demo.web.utils.TimestampUtilTest;

@RunWith(Suite.class)
@SuiteClasses({BookingTest.class, EncodeUtilTest.class, TimestampUtilTest.class})
public class AllTests {

}
