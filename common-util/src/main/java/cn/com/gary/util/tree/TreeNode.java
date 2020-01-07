package cn.com.gary.util.tree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-07 11:54
 **/
@Data
public class TreeNode<T> {
    /**
     * 节点id
     */
    protected Object id;
    /**
     * 父节点id
     */
    protected Object parentId;
    /**
     * 节点名称
     */
    protected String name;
    /**
     * 节点编码
     */
    protected String code;
    /**
     * 节点实际数据
     */
    protected T data;

    List<TreeNode> children = new ArrayList<TreeNode>();

    public TreeNode() {
    }

    public TreeNode(Object id, Object parentId, T data) {
        this.id = id;
        this.parentId = parentId;
        this.data = data;
    }

    /**
     * 追加子节点
     *
     * @param treeNode
     */
    public void add(TreeNode treeNode) {
        children.add(treeNode);
    }

    /**
     * 是否叶子节点
     *
     * @return
     */
    public boolean isLeaf() {
        return children == null || children.size() == 0;
    }
}
