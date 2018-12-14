package com.validate;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import redis.clients.jedis.Jedis;

/**
 * 输入quit退出系统，输入其他获取验证码；
 * 为方便测试，设定三分钟内只允许获取三次验证码；
 * @author Administrator
 *
 */
public class ValidateCode {

	private static String validateCode;

	public static void main(String[] args) {
		// 连接本地的redis服务
		Jedis jedis = new Jedis("localhost");
		System.out.println("redis本地服务连接成功！");

		// 获取验证码
		getValidateCode(jedis);
	}

	/**
	 * 定时，验证码存在时间为3分钟
	 * @param jedis
	 */
	private static void setTimer(final Jedis jedis) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// 超时后，删除该验证码
				jedis.lpop("validateCodes");
			}
		};
		// 设定时间为3分钟
		timer.schedule(task, 180000);
	}

	/**
	 * 生成随机六位数字验证码
	 * @return
	 */
	public static String getRandNum() {
		String randNum = new Random().nextInt(1000000) + "";
		System.out.println("生成" + randNum);
		// 如果生成的不是6位数随机数则返回该方法继续生成
		if (randNum.length() != 6) {
			return getRandNum();
		}
		return randNum;
	}

	/**
	 * 输入quit退出程序；
	 * 输入其他获取验证码；
	 * 3分钟内最多获取3次验证码；
	 * 
	 * validateCodes:存储验证码的list
	 * @param jedis
	 */
	private static void getValidateCode(Jedis jedis) {
		System.out.println("输入quit退出程序");
		System.out.println("输入以获取验证码：");
		// 获取用户输入的信息
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		// 若为quit，则退出程序
		if (s.equalsIgnoreCase("quit")) {
			// 退出程序时，删除validateCodes
			jedis.del("validateCodes");
			System.exit(0);
		} else if (!s.equals("")) {
			// 判断validateCodes中存储的验证码个数，若>=3，则不允许继续获取
			if (jedis.llen("validateCodes") >= 3) {
				System.out.println("请求次数过多");
				getValidateCode(jedis);
			} else {
				validateCode = getRandNum();
				System.out.println("验证码是：" + validateCode);
				jedis.rpush("validateCodes", validateCode);
				// 给验证码定时
				setTimer(jedis);
				// 回到本方法
				getValidateCode(jedis);
			}
		} else {
			// 若输入为“”，直接回到本方法
			getValidateCode(jedis);
		}
	}

}
