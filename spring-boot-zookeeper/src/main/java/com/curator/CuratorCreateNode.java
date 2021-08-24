package com.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.curator.framework.imps.CuratorFrameworkState.STARTED;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/08/15
 */
public class CuratorCreateNode {

    public CuratorFramework client = null;

    public static final String zkServerPath = "192.168.152.128:2181";

    /**
     * 实例化 zk客户端
     */
    public CuratorCreateNode() {

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
        CuratorCreateNode curatorConntion = new CuratorCreateNode();
        CuratorFrameworkState state = curatorConntion.client.getState();
        System.out.println("当前客户的状态：" + (state == STARTED ? "已连接" : "已关闭"));
        //创建节点
        String nodePath = "/lin/qin";
        byte[] data = "iamlinqin".getBytes();
        /**
         * creatingParentsIfNeeded():递归创建节点;
         * PERSISTENT:持久节点
         * OPEN_ACL_UNSAFE:权限,所有用户都可以操作
         */
        curatorConntion.client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath(nodePath, data);

//        System.out.println("节点创建成功....");


        /* 修改数据*/
//        byte[] newData = "666".getBytes();
//        curatorConntion.client.setData()
//                .withVersion(0)
//                .forPath(nodePath,newData);

        /*查询节点*/
        Stat stat = new Stat();
        byte[] getResultData = curatorConntion.client.getData().storingStatIn(stat).forPath(nodePath);
        System.out.println("节点" + nodePath + "的数据为:"+new String(getResultData));
        System.out.println("该节点的版本号为:"+ stat.getVersion());


        /* 查询当前节点下的子节点 */
        List<String> childNodes = curatorConntion.client.getChildren().forPath("/lin");
        for (String childNode : childNodes) {
            System.out.println(childNode);
        }

        Stat stat1 = curatorConntion.client.checkExists().forPath("/lin/qin1");
        if (stat1 == null) {
            System.out.println("该节点不存在！");
        }

        // 删除节点
        curatorConntion.client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath(nodePath);

        TimeUnit.SECONDS.sleep(3);
        curatorConntion.closeZKClient();
        CuratorFrameworkState state2 = curatorConntion.client.getState();
        System.out.println("当前客户的状态：" + (state2 == STARTED ? "已连接" : "已关闭"));

    }
}