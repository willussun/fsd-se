package com.capfsd.mod;

import com.capfsd.mod.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulModuler {

	public static void main(String[] args) {
		SpringApplication.run(ZuulModuler.class, args);
	}
}
