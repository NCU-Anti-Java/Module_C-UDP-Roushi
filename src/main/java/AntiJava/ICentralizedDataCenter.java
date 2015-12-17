package AntiJava;

import java.util.Vector;

/**
 * The Centralized Data Center keeps the centralized and unique data of dynamic objects.
 * In this module programming project we only concern two kinds of dynamic objects.
 * They includes virtual character (每一台client computer 控制的角色) and shared items (client computer 共享的物品)。
 * In the future, for your own project and goal, you need to add more types of object into data center.
 *
 * Created by fntsr on 2015/12/13.
 */
public interface ICentralizedDataCenter
{
    Vector<IDynamicObject> getUpdateInfo();
}
