package AntiJava;

/**
 * This module keeps a copy of data from Centralized Data Center.
 * Its main function is to be read by rendering engine to draw pictures/frames.
 * Please also read Centralized Data Center.
 * However, the data structure in Dynamic Object Module is different from Centeralized Data Center.
 * Centralized Data Center only care (X,Y), DIR, SPEED.
 * However, in this module, these attributes are only part of the attributes of sprite class.
 * A sprite class (as in your prototype) contains other attributes like sprite images and etc.
 *
 * Created by fntsr on 2015/12/13.
 */
public interface IDynamicObjectModule
{
    void addVirtualCharacter(int clientNo) throws Exception;
    void updateVirtualCharacter(int clientNo, int dir, int speed, int x, int y) throws Exception;
    void addItem(String name, int index, boolean shared, int x, int y) throws Exception;
    void updateItem(int index, boolean shared, int owner) throws Exception;
}
