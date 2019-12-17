package com.capfsd.mod;

import com.capfsd.mod.dto.AuthToken;
import com.capfsd.mod.dto.LoginUser;
import com.capfsd.mod.dto.UserDto;
import com.capfsd.mod.entity.User;
import com.capfsd.mod.repository.UserRepository;
import com.capfsd.mod.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import util.LogUtils;
import util.ResponseResult;

import javax.xml.ws.Response;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthModulerTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;

	@Test
	public void test1() throws Exception{
		assertNotNull(userService);
		UserDto userDto = new UserDto();
		userDto.setEmail("aaaa@a.com");
		userDto.setFirstName("Test");
		userDto.setLastName("Test2");
		userDto.setUsername("Jackson");
		userDto.setPassword("jackson");
		userDto.setPhoneNo("532341");
		userDto.setStatus(0);
		userService.createUser(userDto);

	}

	@Test
	public void test2() throws Exception{
		AuthToken authToken = new AuthToken();
		authToken.setRole("USER");
		authToken.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3aWxsc3VuIiwic2NvcGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpc3MiOiJmc2QgY2Fwc3VsZSIsImlhdCI6MTU3NjM5MzMyMSwiZXhwIjoxNTc2NDExMzIxfQ.CwZeIOkH0ez7x36H3TDZ3nN73VW9U0e1RYEGx5r3XmM");
		authToken.setUsername("jackson");
		assertNotNull(authToken);
		assertNotNull(authToken.getRole());
		assertNotNull(authToken.getToken());
		assertNotNull(authToken.getUsername());
		AuthToken authToken2 = new AuthToken("Test");
	}

	@Test
	public void test3() throws Exception{
		AuthToken authToken = new AuthToken("USER","eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3aWxsc3VuIiwic2NvcGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpc3MiOiJmc2QgY2Fwc3VsZSIsImlhdCI6MTU3NjM5MzMyMSwiZXhwIjoxNTc2NDExMzIxfQ.CwZeIOkH0ez7x36H3TDZ3nN73VW9U0e1RYEGx5r3XmM", "USER");
		assertNotNull(authToken);
		assertNotNull(authToken.getRole());
		assertNotNull(authToken.getToken());
		assertNotNull(authToken.getUsername());
	}

	@Test
	public void test4() throws Exception{
		LoginUser loginUser = new LoginUser();
		loginUser.setUsername("jackson");
		loginUser.setPassword("jackson");
		assertNotNull(loginUser.getUsername());
		assertNotNull(loginUser.getPassword());
	}

	@Test
	public void test5() throws Exception{
		assertNotNull(userRepository);
		userRepository.updateUserByIdWithStatus(0L, 1);
	}

	@Test
	public void test6() throws Exception{
		assertNotNull(userRepository);
		User user = new User();
		user.setStatus(0);
		user.setPhoneNo("123421");
		user.setEmail("bbb@b.com");
		user.setLastName("ccc");
		user.setFirstName("ddd");
		user.setUsername("peter");
		user.setPassword("peter");
		assertNotNull(userRepository.save(user));
	}


	@Test
	public void test7() throws Exception{

		User user = userService.findById(10L);
		assertNotNull(user);
		List<User> userList= userService.findAll();
		assertNotNull(userList);
		assertNotNull(userList.get(0));
		assertNotNull(userList.get(0).getUsername());
	}

	@Test
	public void test8() throws Exception{
		userService.findOne("admin");

		LogUtils.getInst(this).info("Test");
		LogUtils.getInst(this).debug("Debug");
		LogUtils.getInst(this).error("error occurred", null);
		LogUtils.getInst(this).warn("Warning");
		LogUtils.getInst(this).trace("trace");
		LogUtils.getInst().info("Test");
		LogUtils.getInst().debug("Debug");
		LogUtils.getInst().error("error occurred", null);
		LogUtils.getInst().debug("Testing");
		LogUtils.getInst().warn("Warning");
		LogUtils.getInst().warn("warn", null);
		LogUtils.getInst().debug("debug", null);
		LogUtils.getInst().info("info",null);
		LogUtils.getInst().error("error", null);
		LogUtils.getInst().trace("trace", null);


		ResponseResult.success("success", null, null);
		ResponseResult.fail("failed", null);
	}

	@Test
	public void test9() throws Exception{
		User user = userService.findOne("admin");
		assertNotNull(user.getUsername());
		assertNotNull(user.getId());
		assertNotNull(user.getRole());
		assertNotNull(user.getPassword());
		assertNotNull(user.getStatus());
		assertNotNull(user.getFirstName());
		assertNotNull(user.getLastName());
		assertNotNull(user.getPhoneNo());
		userRepository.findByUsername("admin");
		UserDto userDto = userService.convert(user);
		userDto.toString();
		userDto.getId();
		userDto.getEmail();
		userDto.getFirstName();
		userDto.getLastName();
		userDto.getPassword();
		userDto.getPhoneNo();
		userDto.getStatus();
		userDto.getUsername();
	}

	@Test
	public void test10() throws Exception{
		ResponseResult<UserDto> rs = new ResponseResult<UserDto>(0, "success", null, null);
		rs.getCode();
		rs.getExtraInfo();
		rs.getExtraInfo();
		rs.getMessage();
		rs.getData();
		rs.setCode(-1);
		rs.setExtraInfo(null);
		rs.setMessage("Changed to error");
		rs.toString();
	}
}