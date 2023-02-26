package com.jie.sentinel.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
public class GoodController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${server.port}")
    private String serverPort;

//    @GetMapping("/buyGoods")      // 版本1
//    public String buyGoods(){
//        String goods = stringRedisTemplate.opsForValue().get("goods:001");
//        int goodNums = goods == null ? 0 : Integer.parseInt(goods);
//        if(goodNums > 0){
//            int realNums = goodNums - 1;
//            stringRedisTemplate.opsForValue().set("goods:001",String.valueOf(realNums));
//            System.out.println("成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort );
//            return "成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort;
//        }else{
//            System.out.println("商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort );
//        }
//        return "商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort;
//    }

    private final Lock lock = new ReentrantLock();

//    @GetMapping("/buyGoods2")       // 版本2，单机版锁
//    public String buyGoods2(){
////        synchronized (this){        // 不见不散
////            String goods = stringRedisTemplate.opsForValue().get("goods:001");
////            int goodNums = goods == null ? 0 : Integer.parseInt(goods);
////            if(goodNums > 0){
////                int realNums = goodNums - 1;
////                stringRedisTemplate.opsForValue().set("goods:001",String.valueOf(realNums));
////                System.out.println("成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort );
////                return "成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort;
////            }else{
////                System.out.println("商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort );
////            }
////            return "商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort;
////        }
//
//
//        try {
//            if(lock.tryLock(3L, TimeUnit.SECONDS)){     // 过时不候
//                String goods = stringRedisTemplate.opsForValue().get("goods:001");
//                int goodNums = goods == null ? 0 : Integer.parseInt(goods);
//                if(goodNums > 0){
//                    int realNums = goodNums - 1;
//                    stringRedisTemplate.opsForValue().set("goods:001",String.valueOf(realNums));
//                    System.out.println("成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort );
//                    return "成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort;
//                }else{
//                    System.out.println("商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort );
//                }
//                return "商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort;
//            }else{
//                return "系统繁忙，请稍后在试 ："+ "\t 服务提供端口 "+ serverPort;
//            }
//        } catch (InterruptedException e) {
//            return "系统错误，请联系管理员 ："+ "\t 服务提供端口 "+ serverPort;
//        } finally {
//            lock.unlock();
//        }
//    }

    private static final String GOODS_LOCK = "goodsLock"; // 商品锁

//    @GetMapping("/buyGoods3")      // 版本3  加入分布式锁
//    public String buyGoods3(){
//        String value = UUID.randomUUID().toString() + Thread.p1currentThread().getName();
//        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(GOODS_LOCK, value);
//        if(!flag){
//            return "抢锁失败";
//        }
//        String goods = stringRedisTemplate.opsForValue().get("goods:001");
//        int goodNums = goods == null ? 0 : Integer.parseInt(goods);
//        if(goodNums > 0){
//            int realNums = goodNums - 1;
//            stringRedisTemplate.opsForValue().set("goods:001",String.valueOf(realNums));
//            stringRedisTemplate.delete(GOODS_LOCK);
//            System.out.println("成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort );
//            return "成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort;
//        }else{
//            System.out.println("商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort );
//        }
//        return "商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort;
//    }

//    @GetMapping("/buyGoods4")      // 版本4 finilly
//    public String buyGoods4(){
//        try {
//            String value = UUID.randomUUID().toString() + Thread.p1currentThread().getName();
//            Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(GOODS_LOCK, value);
//            if(!flag){
//                return "抢锁失败";
//            }
//            String goods = stringRedisTemplate.opsForValue().get("goods:001");
//            int goodNums = goods == null ? 0 : Integer.parseInt(goods);
//            if(goodNums > 0){
//                int realNums = goodNums - 1;
//                stringRedisTemplate.opsForValue().set("goods:001",String.valueOf(realNums));
//                System.out.println("成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort );
//                return "成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort;
//            }else{
//                System.out.println("商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort );
//            }
//            return "商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort;
//        } finally {
//            stringRedisTemplate.delete(GOODS_LOCK);
//        }
//    }

//    @GetMapping("/buyGoods5")      // 版本5 设置过期时间
//    public String buyGoods5(){
//        try {
//            String value = UUID.randomUUID().toString() + Thread.p1currentThread().getName();
//            Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(GOODS_LOCK, value);
//            stringRedisTemplate.expire(GOODS_LOCK,10L,TimeUnit.SECONDS); // 设置失效时间
//            if(!flag){
//                return "抢锁失败";
//            }
//            String goods = stringRedisTemplate.opsForValue().get("goods:001");
//            int goodNums = goods == null ? 0 : Integer.parseInt(goods);
//            if(goodNums > 0){
//                int realNums = goodNums - 1;
//                stringRedisTemplate.opsForValue().set("goods:001",String.valueOf(realNums));
//                System.out.println("成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort );
//                return "成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort;
//            }else{
//                System.out.println("商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort );
//            }
//            return "商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort;
//        } finally {
//            stringRedisTemplate.delete(GOODS_LOCK);
//        }
//    }

//    @GetMapping("/buyGoods6")      // 版本6 加锁和设置过期时间要原子性
//    public String buyGoods6(){
//        try {
//            String value = UUID.randomUUID().toString() + Thread.p1currentThread().getName();
//            Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(GOODS_LOCK, value,10L,TimeUnit.SECONDS);   // 加锁并设置失效时间，原子性
//            if(!flag){
//                return "抢锁失败";
//            }
//            String goods = stringRedisTemplate.opsForValue().get("goods:001");
//            int goodNums = goods == null ? 0 : Integer.parseInt(goods);
//            if(goodNums > 0){
//                int realNums = goodNums - 1;
//                stringRedisTemplate.opsForValue().set("goods:001",String.valueOf(realNums));
//                System.out.println("成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort );
//                return "成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort;
//            }else{
//                System.out.println("商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort );
//            }
//            return "商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort;
//        } finally {
//            stringRedisTemplate.delete(GOODS_LOCK);
//        }
//    }


//    @GetMapping("/buyGoods7")      // 版本7 不能删除其他线程的锁
//    public String buyGoods7(){
//        String value = UUID.randomUUID().toString() + Thread.p1currentThread().getName();
//        try {
//            Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(GOODS_LOCK, value,10L,TimeUnit.SECONDS);   // 加锁并设置失效时间，原子性
//            if(!flag){
//                return "抢锁失败";
//            }
//            String goods = stringRedisTemplate.opsForValue().get("goods:001");
//            int goodNums = goods == null ? 0 : Integer.parseInt(goods);
//            if(goodNums > 0){
//                int realNums = goodNums - 1;
//                stringRedisTemplate.opsForValue().set("goods:001",String.valueOf(realNums));
//                System.out.println("成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort );
//                return "成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort;
//            }else{
//                System.out.println("商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort );
//            }
//            return "商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort;
//        } finally {
//            // 删除锁时判断是否是自己的锁
//            stringRedisTemplate.opsForValue().get(GOODS_LOCK).equalsIgnoreCase(value){
//                stringRedisTemplate.delete(GOODS_LOCK);
//            }
//        }
//    }


//    @GetMapping("/buyGoods7")      // 版本7 判断加锁和解锁可能不是同一个客服端，会出现误解的情况(使用乐观锁)
//    public String buyGoods7(){
//        String value = UUID.randomUUID().toString() + Thread.p1currentThread().getName();
//        try {
//            Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(GOODS_LOCK, value,10L,TimeUnit.SECONDS);   // 加锁并设置失效时间，原子性
//            if(!flag){
//                return "抢锁失败";
//            }
//            String goods = stringRedisTemplate.opsForValue().get("goods:001");
//            int goodNums = goods == null ? 0 : Integer.parseInt(goods);
//            if(goodNums > 0){
//                int realNums = goodNums - 1;
//                stringRedisTemplate.opsForValue().set("goods:001",String.valueOf(realNums));
//                System.out.println("成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort );
//                return "成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort;
//            }else{
//                System.out.println("商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort );
//            }
//            return "商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort;
//        } finally {
//            // 使用乐观锁解决
//            while (true){
//                stringRedisTemplate.watch(GOODS_LOCK);
//                if(stringRedisTemplate.opsForValue().get(GOODS_LOCK).equalsIgnoreCase(value)){
//                    stringRedisTemplate.setEnableTransactionSupport(true);
//                    stringRedisTemplate.multi();
//                    stringRedisTemplate.delete(GOODS_LOCK);
//                    List<Object> exec = stringRedisTemplate.exec();
//                    if(exec == null){
//                        continue;
//                    }
//                }
//                stringRedisTemplate.unwatch();
//                break;
//            }
//        }
//    }

//    @GetMapping("/buyGoods8")      // 版本7 判断加锁和解锁可能不是同一个客服端，会出现误解的情况(使用lua脚本// )
//    public String buyGoods8() throws Exception {
//        String value = UUID.randomUUID().toString() + Thread.p1currentThread().getName();
//        try {
//            Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(GOODS_LOCK, value,10L,TimeUnit.SECONDS);   // 加锁并设置失效时间，原子性
//            if(!flag){
//                return "抢锁失败";
//            }
//            String goods = stringRedisTemplate.opsForValue().get("goods:001");
//            int goodNums = goods == null ? 0 : Integer.parseInt(goods);
//            if(goodNums > 0){
//                int realNums = goodNums - 1;
//                stringRedisTemplate.opsForValue().set("goods:001",String.valueOf(realNums));
//                System.out.println("成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort );
//                return "成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort;
//            }else{
//                System.out.println("商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort );
//            }
//            return "商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort;
//        } finally {
//            // 使用lua脚本解决
//            Jedis jedis = RedisUtils.getJedis();
//            // lua脚本
//            String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n" +
//                    "then\n" +
//                    "    return redis.call(\"del\",KEYS[1])\n" +
//                    "else\n" +
//                    "    return 0\n" +
//                    "end";
//            try {
//                Object eval = jedis.eval(script, Collections.singletonList(GOODS_LOCK), Collections.singletonList(value));
//                if("1".equalsIgnoreCase(eval.toString())){
//                    System.out.println("----- del redis lock ok");
//                }else{
//                    System.out.println("----- del redis lock error");
//                }
//            } finally {
//                if(null != jedis){
//                    jedis.close();
//                }
//            }
//        }
//    }

    @Resource
    private Redisson redisson;


//    @GetMapping("/buyGoods9")      // 版本9， 缓存续命，使用redison
//    public String buyGoods9(){
//        RLock lock = redisson.getLock(GOODS_LOCK);
//        lock.lock();
//        try {
//            String goods = stringRedisTemplate.opsForValue().get("goods:001");
//            int goodNums = goods == null ? 0 : Integer.parseInt(goods);
//            if(goodNums > 0){
//                int realNums = goodNums - 1;
//                stringRedisTemplate.opsForValue().set("goods:001",String.valueOf(realNums));
//                System.out.println("成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort );
//                return "成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort;
//            }else{
//                System.out.println("商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort );
//            }
//            return "商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort;
//        } finally {
//            lock.unlock();
//        }
//    }


    @GetMapping("/buyGoods10")      // 版本10， 高并发下避免其他线程尝试解当前线程锁的问题
    public String buyGoods10(){
        RLock lock = redisson.getLock(GOODS_LOCK);
        lock.lock();
        try {
            String goods = stringRedisTemplate.opsForValue().get("goods:001");
            int goodNums = goods == null ? 0 : Integer.parseInt(goods);
            if(goodNums > 0){
                int realNums = goodNums - 1;
                stringRedisTemplate.opsForValue().set("goods:001",String.valueOf(realNums));
                System.out.println("成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort );
                return "成功买到商品，库存还剩下：" + realNums +" 件" + "\t 服务提供端口 "+ serverPort;
            }else{
                System.out.println("商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort );
            }
            return "商品售完，欢迎下次光临 ："+ "\t 服务提供端口 "+ serverPort;
        } finally {
            // 高并发下避免其他线程尝试解当前线程锁的问题
            if(lock != null && lock.isLocked() && lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}
