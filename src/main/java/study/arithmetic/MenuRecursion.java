package study.arithmetic;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuRecursion {

    public static List<Menu> buildMenuTree(List<Menu> menus){
        Map<Long,List<Menu>> map = new HashMap<>();
        for (Menu menu: menus) {
            if(!map.containsKey(menu.getParentId())){
                map.put(menu.getParentId(), new ArrayList<>());
            }
            map.get(menu.getParentId()).add(menu);
        }
//        Map<Long, List<Menu>> collect = menus.stream().collect(Collectors.groupingBy(Menu::getParentId));
        return buildMenuTree(map,0L);
    }

    public static List<Menu> buildMenuTree(Map<Long, List<Menu>> map,Long parentId){
        List<Menu> menuTree = new ArrayList<>();
        List<Menu> children = map.get(parentId);
        if(!CollectionUtils.isEmpty(children)){
            for (Menu child : children) {
                child.setChildren(buildMenuTree(map,child.getId()));
                menuTree.add(child);
            }
        }
        return menuTree;
    }

    public static void main(String[] args) {
        List<Menu> menus = new ArrayList<>();
        Menu m1 = new Menu(1L,"系统管理",0L);
        Menu m2 = new Menu(2L,"客户管理",1L);
        Menu m3 = new Menu(3L,"收据管理",1L);
        Menu m4 = new Menu(4L,"合同管理",2L);
        Menu m5 = new Menu(5L,"电子收据",2L);
        Menu m6 = new Menu(6L,"房东管理",0L);
        menus.add(m1);
        menus.add(m2);
        menus.add(m3);
        menus.add(m4);
        menus.add(m5);
        menus.add(m6);
        // 假设 menus 是从数据库中查询出来的菜单列表
        // 调用 buildMenuTree 方法构建菜单树
        List<Menu> menuTree = buildMenuTree(menus);
        // 输出菜单树
        System.out.println(menuTree);
    }

    // 菜单实体类
    @Data
    public static class Menu {
        private Long id;
        private String name;
        private Long parentId;
        private List<Menu> children; // 子菜单列表

        public Menu(Long id, String name, Long parentId) {
            this.id = id;
            this.name = name;
            this.parentId = parentId;
        }
    }
}
