package study.arithmetic;

import java.util.*;

public class BinnaryTree {
    //递归
    public List<Integer> solution(TreeNode root){
        List<Integer> res = new ArrayList<>();
        preOrder(root,res);
        return res;
    }

    private void preOrder(TreeNode node,List<Integer> res){
        if(node == null){
            return;
        }
        res.add(node.val);
        preOrder(node.left,res);
        preOrder(node.right,res);
    }

    private void midOrder(TreeNode node,List<Integer> res){
        if(node == null){
            return;
        }
        preOrder(node.left,res);
        res.add(node.val);
        preOrder(node.right,res);
    }

    private void postOrder(TreeNode node,List<Integer> res){
        if(node == null){
            return;
        }
        preOrder(node.left,res);
        preOrder(node.right,res);
        res.add(node.val);
    }

    //非递归,前序：根左右
    public List<Integer> preOrder1(TreeNode root){
        List<Integer> res = new ArrayList<>();
        if(root == null){
            return res;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()){
            while (root != null){
                res.add(root.val);
                stack.push(root);
                root = root.left;
            }
            TreeNode node = stack.pop();
            root = node.right;
        }
        return res;
    }

    public List<Integer> midOrder1(TreeNode root){
        List<Integer> res = new ArrayList<>();
        if(root == null){
            return res;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()){
            while (root != null){
                stack.add(root);
                root = root.left;
            }
            TreeNode node = stack.pop();
            res.add(node.val);
            root = node.right;
        }
        return res;
    }

    /**
     * 后序遍历，左右根
     * 需要一个前节点来记录上次添加的元素，以免无限循环添加右节点
     * @param root
     * @return
     */
    public List<Integer> postOrder1(TreeNode root){
        List<Integer> res = new ArrayList<>();
        if(root == null){
            return res;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        while (root != null || !stack.isEmpty()){
            while (root !=null){
                stack.add(root);
                root = root.left;
            }
            root = stack.pop();
            if(root.right == null || root.right == pre){
                res.add(root.val);
                pre = root;
                root = null;
            }else {
                stack.add(root);
                root = root.right;
            }
        }
        return res;
    }

    /**
     * 层次遍历
     * 用一个队列来记录每层的元素，元素取出后，队列中就不存在次元素，如果用ArrayList，则在删除元素的时候比较麻烦
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root){
        List<List<Integer>> res = new ArrayList<>();
        if(root == null){
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            List<Integer> list = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
            }
            res.add(list);
        }
        return res;
    }

    public static void main(String[] args) {
        int a =3,b=6;
//        a = a + b;
//        b = a - b;
//        a = a - b;

        a = a^b;
        b = a^b;
        a = a^b;

        System.out.println(a);
        System.out.println(b);
    }
}
