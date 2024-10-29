import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * @author pwdan
 * @create 2024-10-29 10:40
 **/
public class ReactorTest {

    @Test
    public void test() {
        // 创建一个 Flux
        Flux<String> flux = Flux.just("Hello", "World");

        // 订阅并处理数据
        flux.publishOn(Schedulers.boundedElastic()) // 指定在有界弹性线程池上执行
                .doOnNext(System.out::println) // 处理每个数据项
                .doOnError(Throwable::printStackTrace) // 处理错误
                .doOnComplete(() -> System.out.println("Completed")) // 处理完成
                .blockLast(); // 阻塞等待最后一个数据项
    }

    @Test
    public void test1() {
        // 0个元素
        Mono<String> mono = Mono.empty();
        // 订阅流
        mono.subscribe(System.out::println);

        // 单个元素
        mono = Mono.just("mono-单个元素");
        // 订阅流
        mono.subscribe(System.out::println);

        // 0个元素
        Flux<String> flux = Flux.empty();
        // 订阅流
        flux.subscribe(System.out::println);

        // 多个元素
        flux = Flux.just("flux1", "flux2");
        // 订阅流
        flux.subscribe(System.out::println);
        // 此流中元素为10, 11, 12, 13
        Flux<Integer> flux2 = Flux.range(10, 4);
        flux2.subscribe(System.out::println);
    }

    @Test
    public void test2() {
        Flux.create(sink -> {
            for (int i = 0; i < 5; i++) {
                sink.next("元素" + i);
            }
        }).subscribe(System.out::println);
    }

    /**
     * 指定每个元素之间相隔1s
     */
    @Test
    public void delayElements() throws InterruptedException {
        Flux<String> flux = Flux.just("vhukze", "lqyihv", "lqhlvu")
                .delayElements(Duration.ofSeconds(1));
        flux.subscribe(System.out::println);

        Thread.sleep(5000);
    }
}
