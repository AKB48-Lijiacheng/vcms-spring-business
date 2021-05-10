import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : lijiacheng
 * @description :test
 * ---------------------------------
 * @since : Create in 2020/11/13 11:23
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TestApplication.class)
@Slf4j
public class test {
   @Autowired(required = false)
   RestTemplate restTemplate;

   @Data
   public class user {
      private String username;
   }

   @Test
   public void wd() throws Exception {
      ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
      for (int i = 0; i < 10; i++) {
         fixedThreadPool.execute(new Runnable() {
            public void run() {
               // 打印正在执行的缓存线程信息
               System.out.println(Thread.currentThread().getName()
                       + "正在被执行");

               System.out.println(Thread.currentThread().getName()
                       + "执行完毕");
            }
         });
      }
   }


   @Test
   public void test() throws IOException {
      ServerSocket serverSocket = new ServerSocket(83);

   }


      public int findLengthOfLCIS(int[] nums) {
         int lmax=0,l=1;

         for (int i = 0; i < nums.length-1; i++) {
             if(nums[i]<nums[i+1]){
                l++;
             }else{
                if(l>lmax)lmax=l;
                l=1;
             }
         }
         if(l>lmax)lmax=l;
         if (nums.length==0)lmax=0;
         return lmax;
      }
   }




