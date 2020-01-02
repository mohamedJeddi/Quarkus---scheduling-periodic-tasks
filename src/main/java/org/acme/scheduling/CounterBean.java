package org.acme.scheduling;

import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class CounterBean {

    private AtomicInteger counter = new AtomicInteger();


    public int get() {
        return counter.get();
    }

    //@Scheduled annotation to instruct Quarkus to run this method every 10 seconds provided a worker thread is available
    @Scheduled(every="10s")
    void increment() {
        counter.incrementAndGet();
    }

    //The annotated method is executed at 10:15am every day.
    @Scheduled(cron="0 15 10 * * ?")
    void cronJob(ScheduledExecution execution) {
        counter.incrementAndGet();
        System.out.println(execution.getScheduledFireTime());
    }

    @Scheduled(cron = "{cron.expr}")
    void cronJobWithExpressionInConfig() {
        counter.incrementAndGet();
        System.out.println("Cron expression configured in application.properties");
    }
}
