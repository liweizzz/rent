package study.arithmetic;

/**
 * 判断对称二叉树
 */
public class SymmetryTree {
    public static boolean solution(TreeNode root){
        return root == null || ss(root.left,root.right);
    }

    public static boolean ss(TreeNode left,TreeNode right){
        if(left == null && right == null){
            return true;
        }
        if(left == null || right == null || left.val != right.val){
            return false;
        }
        return ss(left.left,right.right) && ss(left.right,right.left);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(3);
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;
        System.out.println(solution(root));
    }
}
