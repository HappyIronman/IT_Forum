package com.ironman.forum.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //ʹ��junit4���в���
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) //���������ļ�
public class BaseJunit4Test {
}
