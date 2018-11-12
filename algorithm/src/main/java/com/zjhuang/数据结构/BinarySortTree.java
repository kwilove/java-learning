package com.zjhuang.数据结构;

/**
 * 二叉排序树
 *
 * @author zjhuang
 * @create 2018/10/8 8:24
 **/
public class BinarySortTree {
    BinarySortTree parent;
    BinarySortTree left;
    BinarySortTree right;
    Object data;
    int count = 0;

    public BinarySortTree() {
        this.parent = this.left = this.right = null;
    }

    public BinarySortTree(Object data) {
        this.data = data;
        this.left = this.right = null;
        this.count++;
    }

    /**
     * 添加新节点
     *
     * @param data
     */
    public void put(Object data) {
        if (null == this.data) {
            this.data = data;
            this.count++;
            return;
        }
        put(this, data);
        this.count++;
    }

    private void put(BinarySortTree crt, Object data) {
        if (compare(data, crt.data) < 0) {
            if (null == crt.left) {
                crt.left = new BinarySortTree(data);
                crt.left.parent = crt;
                return;
            }
            put(crt.left, data);
        } else {
            if (null == crt.right) {
                crt.right = new BinarySortTree(data);
                crt.right.parent = crt;
                return;
            }
            put(crt.right, data);
        }
    }

    /**
     * 比较两个节点数据
     *
     * @param t1
     * @param t2
     * @return
     */
    private int compare(Object t1, Object t2) {
        if (t1 instanceof Integer) {
            int m = (int) t1;
            int n = (int) t2;
            return m - n;
        } else if (t1 instanceof Long) {
            long m = (long) t1;
            long n = (long) t2;
            return (int) (m - n);
        } else if (t1 instanceof String) {
            String m = (String) t1;
            String n = (String) t2;
            return m.compareTo(n);
        }
        throw new IllegalArgumentException("Arguments type must be Integer or Long or String.");
    }

    /**
     * 删除第一个匹配的节点
     *
     * @param data
     */
    public void remove(Object data) {
        remove(this, data);
        this.count--;
    }

    public void remove(BinarySortTree crt, Object data) {
        if (null == crt) {
//            throw new NullPointerException("this is an empty tree.");
            return;
        }
        if (compare(data, crt.data) == 0) {
            // 如果当前节点时叶子节点，直接删除
            if (null == crt.left && null == crt.right) {
                crt.parent = null;
                crt = null;
                return;
            }
            // 如果当前节点存在左子树，但不存在右子树
            if (null != crt.left && null == crt.right) {
                if (crt.parent.left == crt) {
                    crt.parent.left = crt.left;
                } else {
                    crt.parent.right = crt.left;
                }
                return;
            }
            // 如果当前节点存在右子树，但不存在左子树
            if (null == crt.left && null != crt.right) {
                if (crt.parent.left == crt) {
                    crt.parent.left = crt.right;
                } else {
                    crt.parent.right = crt.right;
                }
                return;
            }
            // 如果当前节点存在左、右子树，先从左子树中寻找最大子节点，替换当前节点的值
            if (null != crt.left && null != crt.right) {
                BinarySortTree next = crt.left;
                while (null != next.right) {
                    next = next.right;
                }
                crt.data = next.data;
                if (null != next.left) {
                    if (next.parent.left == next) {
                        next.parent.left = next.left;
                    } else {
                        next.parent.right = next.left;
                    }
                } else {
                    next.parent.right = null;
                }
                return;
            }
        } else if (compare(data, crt.data) < 0) {
            remove(crt.left, data);
        } else if (compare(data, crt.data) > 0) {
            remove(crt.right, data);
        }
    }

    /**
     * 中序遍历
     */
    public void in() {
        in(this);
    }

    /**
     * 前序遍历
     */
    public void pre() {
        pre(this);
    }

    /**
     * 后序遍历
     */
    public void post() {
        post(this);
    }

    private void in(BinarySortTree crt) {
        if (null == crt) {
            return;
        }
        in(crt.left);
        System.out.print(crt.data + " ");
        in(crt.right);
    }

    private void pre(BinarySortTree crt) {
        if (null == crt) {
            return;
        }
        System.out.print(crt.data + " ");
        pre(crt.left);
        pre(crt.right);
    }

    private void post(BinarySortTree crt) {
        if (null == crt) {
            return;
        }
        post(crt.left);
        post(crt.right);
        System.out.print(crt.data + " ");
    }

    public static void main(String[] args) {
        BinarySortTree tree = new BinarySortTree();
//        IntArrayGenerater.generate(20, 20)
        for (String i : "9 19 7 0 13 18 6 16 6 3 5 15 5 17 1 10 16 0 4 11".split("\\s+")) {
            System.out.print(i + " ");
            tree.put(Integer.parseInt(i));
        }
        System.out.println("\n------------------------------------------------");
        tree.in();
        tree.remove(16);
        System.out.println("\n------------------------------------------------");
        tree.in();
    }

}
