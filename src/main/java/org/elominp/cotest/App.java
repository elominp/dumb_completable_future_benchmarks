package org.elominp.cotest;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Started!");
        int nb_tasks = Integer.parseInt(args[0]);
        ArrayList<Future<Void>> tasks = new ArrayList<Future<Void>>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < nb_tasks; i++)
        {
            tasks.add(
                CompletableFuture.runAsync(() -> {
                    System.out.println("Created Task!");
                },
                CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS))
                .thenRunAsync(() -> { System.out.println("Hello, World!"); },
                CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS))
            );
        }
        long elapsed = System.currentTimeMillis() - start;
        System.err.println("Created " + nb_tasks + " tasks in " + elapsed + "ms");
        for (Future<Void> task : tasks)
        {
            try
            {
                task.get();
            }
            catch (InterruptedException exception)
            {
                System.err.println(exception);
            }
            catch (ExecutionException exception)
            {
                System.err.println(exception);
            }
        }
    }
}
