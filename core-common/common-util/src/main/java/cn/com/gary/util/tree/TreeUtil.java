package cn.com.gary.util.tree;

import cn.com.gary.util.ToyUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-07 11:53
 **/
@Slf4j
public class TreeUtil {
    private TreeUtil() {
    }

    /**
     * 两层循环实现建树
     *
     * @param treeNodes 传入的树节点列表
     * @param root
     * @return
     */
    public static <T extends TreeNode> List<T> build(List<T> treeNodes, Object root) {
        if (ToyUtil.listEmpty(treeNodes)) {
            return treeNodes;
        }

        List<T> trees = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }

            for (T it : treeNodes) {
                if (it.getParentId().equals(treeNode.getId())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @param root
     * @return
     */
    public static <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        if (ToyUtil.listEmpty(treeNodes)) {
            return treeNodes;
        }

        List<T> trees = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    private static <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}
