package com.example.project_sem_4.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
@EnableAsync
public class CenimarScheduler {

//	@Scheduled(fixedRate = 1000)
//	public void scheduleFixedRateTask() throws InterruptedException {
//		System.out.println(new Date().toGMTString() + "1 Fixed rate task - " + System.currentTimeMillis() / 1000);
//		// TODO do somethings
//		Thread.sleep(2000);
//		System.out.println(new Date().toGMTString() + "-1 Fixed rate task - " + System.currentTimeMillis() / 1000);
//	}
//
//	@Scheduled(fixedRate = 3000)
//	public void scheduleFixedRateTask2() throws InterruptedException {
//		System.out.println(new Date().toGMTString() + "2 Fixed rate task - " + System.currentTimeMillis() / 1000);
//		// TODO do somethings
//		Thread.sleep(1000);
//		System.out.println(new Date().toGMTString() + "-2 Fixed rate task - " + System.currentTimeMillis() / 1000);
//	}
//	@Async
//	@Scheduled(fixedRate = 1000)
//	public void scheduleFixedRateTaskAsync() throws InterruptedException {
//		System.out.println(new Date().toGMTString()+"--2 Fixed rate task async - " + System.currentTimeMillis() / 1000);
//		Thread.sleep(2000);
//		System.out.println(new Date().toGMTString()+"-->2 Fixed rate task async - " + System.currentTimeMillis() / 1000);
//	}

 // Sau khi chạy 6 giây thì sẽ chỉ chạy 3 giây
//	@Scheduled(fixedDelay = 3000, initialDelay = 6000)
//	public void scheduleFixedRateWithInitialDelayTask() {
//
//		long now = System.currentTimeMillis() / 1000;
//		System.out.println(new Date().toGMTString()+"---3 Fixed rate task with one second initial delay - " + now);
//	}

//	@Scheduled(cron = "* * 12 * * ?") //dinh dang: giay - phut - gio - ngay trong thang - thang - ngay trong tuan
//	public void scheduleTaskUsingCronExpression() {
//
//	    long now = System.currentTimeMillis() / 1000;
//	    System.out.println(new Date().toGMTString()+
//	      "schedule tasks using cron jobs - " + now);
//	}
//
//	@Autowired
//	public BooksService service;
//
//	@Scheduled(fixedRate = 1000)
//	public void scheduleUpdateQuantityOfBooks() throws InterruptedException {
//		// TODO do somethings
//		Thread.sleep(1000);
//		service.updateQuantityOfBook();
//	}
//	@Autowired
//	public MailService mailService;
//	@Autowired
//	public MailLogService mailLogService;
//	@Autowired
//	public BookService bookService;
//
//	@Scheduled(fixedRate = 1000)
//    public void sendSimpleEmailLoginFailed() throws InterruptedException{
//		List<MailLog> listMailLog = mailLogService.getAll();
//		listMailLog.forEach(s -> {
//			if (s.getStatus() == 0){
//				mailService.sendSimpleEmail(s.getEmail(),s.getTitle(), "Lỗi đăng nhập");
//				mailLogService.updateMailLog(s.getId());
//			}
//		});
//		System.out.println("SentEmail");
//		Thread.sleep(12000);
//    }
//
//	@Scheduled(fixedRate = 1000)
//	public void  checkTotalAmountBook() throws InterruptedException{
//		List<Book> bookList = bookService.getAll();
//		bookList.forEach(s -> {
//			System.out.println(s.getName() + "co so luong la: " + s.getTotalAmount());
//		});
//		Thread.sleep(5000);
//	}
}
