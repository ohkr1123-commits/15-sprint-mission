package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DiscodeitApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context =
				SpringApplication.run(DiscodeitApplication.class, args);

		UserService userService =
				context.getBean(UserService.class);

		ChannelService channelService =
				context.getBean(ChannelService.class);

		MessageService messageService =
				context.getBean(MessageService.class);


		// 셋업
		User user = setupUser(userService);
		Channel channel = setupChannel(channelService);

		// 테스트
		messageCreateTest(messageService, channel, user);

		System.out.println("Spring Bean 조회 완료");
	}

	// 테스트용 User 생성
	static User setupUser(UserService userService) {

		User user = userService.create(
				"woody",
				"woody@codeit.com",
				"Password1!"
		);

		System.out.println("유저 생성: " + user.getName());

		return user;
	}


	// 테스트용 Channel 생성
	static Channel setupChannel(ChannelService channelService) {

		Channel channel = channelService.create("공지");

		System.out.println("채널 생성: " + channel.getChannelName());

		return channel;
	}


	// 테스트용 Message 생성
	static void messageCreateTest(
			MessageService messageService,
			Channel channel,
			User user
	) {

		Message message = messageService.create(
				channel.getId(),
				user.getId(),
				"안녕하세요."
		);

		if (message != null) {
			System.out.println("메시지 생성: " + message.getContent());
		} else {
			System.out.println("메시지 생성 실패");
		}
	}
}