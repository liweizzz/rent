package com.liwei.rent.common.utils;

import com.liwei.rent.common.dto.PrivilegeDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuUtils {
    // 构建菜单树的方法
    public static List<PrivilegeDTO> buildMenuTree(List<PrivilegeDTO> menus) {
//        menus.sort(Comparator.comparing(PrivilegeDTO::getSort));
        // 创建一个 Map，用于存储菜单的父菜单ID和其子菜单列表的映射关系
//        Map<Long, List<Menu>> parentChildrenMap = new HashMap<>();
//        for (Menu menu : menus) {
//            // 将每个菜单按照其父菜单ID分组存储到 Map 中
//            parentChildrenMap.computeIfAbsent(menu.getParentId(), k -> new ArrayList<>()).add(menu);
//        }
        // 将每个菜单按照其父菜单ID分组存储到 Map 中
        Map<Integer, List<PrivilegeDTO>> parentChildrenMap = menus.stream()
                .collect(Collectors.groupingBy(PrivilegeDTO::getParent));
        // 递归构建菜单树
        return buildMenuTree(parentChildrenMap, 0); // 假设根菜单的父菜单ID为0
    }

    // 普通递归构建菜单树的辅助方法
//    private static List<Menu> buildMenuTree(Map<Long, List<Menu>> parentChildrenMap, Long parentId) {
//        List<Menu> menuTree = new ArrayList<>();
//        // 获取当前父菜单ID下的所有子菜单列表
//        List<Menu> children = parentChildrenMap.get(parentId);
//        if (children != null) {
//            // 遍历子菜单列表，递归构建菜单树
//            for (Menu child : children) {
//                child.setChildren(buildMenuTree(parentChildrenMap, child.getId())); // 递归构建子菜单的子树
//                menuTree.add(child);
//            }
//        }
//        return menuTree;
//    }

    /**
     * 使用了 Stream 的 peek 方法来处理每个子菜单
     * 并在处理过程中递归构建子菜单的子树
     * 最后，使用 collect 方法将处理过的子菜单收集到一个列表中并返回
     * @param parentChildrenMap
     * @param parentId
     * @return
     */
    private static List<PrivilegeDTO> buildMenuTree(Map<Integer, List<PrivilegeDTO>> parentChildrenMap, Integer parentId) {
        List<PrivilegeDTO> children = parentChildrenMap.get(parentId);
        if (children == null) {
            return null;
        }
        return children.stream()
                .peek(child -> child.setChildren(buildMenuTree(parentChildrenMap, child.getId())))
                .collect(Collectors.toList());
    }

//    // 菜单实体类
//    @Data
//    public static class Menu {
//        private Long id;
//        private String name;
//        private Long parentId;
//        private List<Menu> children; // 子菜单列表
//
//        public Menu(Long id, String name, Long parentId) {
//            this.id = id;
//            this.name = name;
//            this.parentId = parentId;
//        }
//    }
//
//    // 示例
//    public static void main(String[] args) {
//        List<Menu> menus = new ArrayList<>();
//        Menu m1 = new Menu(1L,"系统管理",0L);
//        Menu m2 = new Menu(2L,"客户管理",1L);
//        Menu m3 = new Menu(3L,"收据管理",1L);
//        Menu m4 = new Menu(4L,"合同管理",2L);
//        Menu m5 = new Menu(5L,"电子收据",2L);
//        Menu m6 = new Menu(6L,"房东管理",0L);
//        menus.add(m1);
//        menus.add(m2);
//        menus.add(m3);
//        menus.add(m4);
//        menus.add(m5);
//        menus.add(m6);
//        // 假设 menus 是从数据库中查询出来的菜单列表
//        // 调用 buildMenuTree 方法构建菜单树
//        List<Menu> menuTree = buildMenuTree(menus);
//        // 输出菜单树
//        System.out.println(menuTree);
//    }
}
