package com.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryNTimes;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.apache.curator.framework.imps.CuratorFrameworkState.STARTED;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/08/24
 */
@Slf4j
public class WacthOperator {
    public static Logger logger = Logger.getLogger("this.getClass()");

    public CuratorFramework client = null;

    public static final String zkServerPath = "192.168.152.128:2181";

    /**
     * 实例化 zk客户端
     */
    public WacthOperator() {

        /**
         * 使用方式二
         * curator 连接 zookeeper 的策略:RetryNTimes
         * n:重试次数
         * sleepMsBetweenRetries 每次重试间隔时间
         */
        RetryPolicy retryPolicy1 = new RetryNTimes(3, 5000);


        client = CuratorFrameworkFactory.builder().connectString(zkServerPath)
                .sessionTimeoutMs(60000)
                .retryPolicy(retryPolicy1)
                //这里添加了一个namespace,类似于一个工作站,所有操作都在这个站中,操作成功之后会在根节点创建一个workspace节点
                .namespace("workspace")
                .build();

        client.start();
    }

    /**
     * @Description 关闭 zk客户端连接
     */
    public void closeZKClient() {
        if (client != null) {
            this.client.close();
        }
    }

    public static void main(String[] args) throws Exception {
        /**
         * STARTED    已启动
         * STOPPED    已停止
         */
        //实例化
        final String nodePath = "/lin/qin";
        WacthOperator curatorConntion = new WacthOperator();
        CuratorFrameworkState state = curatorConntion.client.getState();
        System.out.println("当前客户的状态：" + (state == STARTED ? "已连接" : "已关闭"));
        //watcher事件,当使用 usingWatcher的时候,监听只会触发一次,监听完毕后就销毁
        /*************监听只会触发一次***************/
//        curatorConntion.client.getData().usingWatcher((CuratorWatcher) watchedEvent -> {
//            System.out.println("getData触发watcher，节点路径为：" + watchedEvent.getPath());
//        }).forPath(nodePath);


        /*************一次监听N次触发***************/
        //NodeCache:监听数据节点的变更,会触发事件
        final NodeCache nodeCache = new NodeCache(curatorConntion.client, nodePath);
        //buildInitial:初始化的时候获取node的值并且缓存,默认false,不缓存
        nodeCache.start(true);
        if (nodeCache.getCurrentData() != null) {
            System.out.println("节点初始化数据为:" + new String(nodeCache.getCurrentData().getData()));
        } else {
            System.out.println("节点初始化数据为空....");
        }
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            public void nodeChanged() throws Exception {
                if (nodeCache.getCurrentData() == null) {
                    System.out.println("空节点");
                    return;
                }
                String data = new String(nodeCache.getCurrentData().getData());
                System.out.println("节点路径:" + nodeCache.getCurrentData().getPath() + "数据: " + data);
            }
        });

        // 监听父节点以下所有的子节点, 当子节点发生变化的时候(增删改)都会监听到，PathChildrenCache监听数据节点的增删改
        final PathChildrenCache childrenCache = new PathChildrenCache(curatorConntion.client, "/search", true);
       // NORMAL:异步初始化, BUILD_INITIAL_CACHE:同步初始化, POST_INITIALI ZED_EVENT:异步初始化,初始化之后会触发事件
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        List<ChildData> childDataList = childrenCache.getCurrentData(); // 当前数据节点的子节点数据列表
        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                if (event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED)) {
                    log.info("子节点初始化ok..");
                } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
                    log.info("添加子节点, path:{}, data:{}", event.getData().getPath(), event.getData().getData());
                } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                    log.info("删除子节点, path:{}", event.getData().getPath());
                } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                    log.info("修改子节点, path:{}, data:{}", event.getData().getPath(), event.getData().getData());
                }
            }
        });


        TimeUnit.MINUTES.sleep(2);
        curatorConntion.closeZKClient();
        CuratorFrameworkState state2 = curatorConntion.client.getState();
        System.out.println("当前客户的状态：" + (state2 == STARTED ? "已连接" : "已关闭"));

    }
}
