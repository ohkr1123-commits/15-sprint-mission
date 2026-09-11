package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.ChannelDto.PublicChannelCreatRequest;
import com.sprint.mission.discodeit.dto.UserDto.UserCreateRequest;
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

		// Spring IoC Container가 만들어둔 Bean을 가져옵니다.
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
		Channel channel = setupChannel(channelService, user);

		// 테스트
		messageCreateTest(messageService, channel, user);

		// 조회가 끝나면 출력!
		System.out.println("Spring Bean 조회 완료");
	}

	// 테스트용 User 생성
	static User setupUser(UserService userService) {

		UserCreateRequest request =
				new UserCreateRequest(
						"woody",
						"woody@codeit.com",
						"Password1!"
				);

		User user = userService.create(request, null);

		System.out.println("유저 생성: " + user.getName());

		return user;
	}

	// 테스트용 PUBLIC Channel 생성
	static Channel setupChannel(ChannelService channelService, User user) {

		PublicChannelCreatRequest request =
				new PublicChannelCreatRequest(
						user.getId(),
						"공지",
						"공지 채널입니다."
				);

		Channel channel = channelService.createPublic(request);

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

	/*
	기존의 while문으로 작동하는 방식의 테스트는 스프링부트에서 없어도 작동이 가능함으로
	JavaApplication으로부터 갖고 오지 않고 새로 만듬
	*/

}