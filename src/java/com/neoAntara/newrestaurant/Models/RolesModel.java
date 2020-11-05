/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.neoAntara.newrestaurant.Models;

public class RolesModel {
    private static RolesModel Instance = null;
    private static boolean _accessRights;
    private static boolean _addItem;
    private static boolean _changePassword;
    private static boolean _dayEnd;
    private static boolean _debitAmount;
    private static boolean _deleteItem;
    private static boolean _editItem;
    private static boolean _editOrder;
    private static boolean _exportDB;
    private static String _loggedInUser;
    private static boolean _newOrder;
    private static boolean _reports;
    private static boolean _reprintOrder;
    private static boolean _updateSetting;

    private RolesModel() {
    }

    public static RolesModel GetInstance() {
        if (Instance == null) {
            Instance = new RolesModel();
        }
        return Instance;
    }

    public static boolean get_accessRights() {
        return _accessRights;
    }

    public static boolean get_addItem() {
        return _addItem;
    }

    public static boolean get_changePassword() {
        return _changePassword;
    }

    public static boolean get_dayEnd() {
        return _dayEnd;
    }

    public static boolean get_debitAmount() {
        return _debitAmount;
    }

    public static boolean get_deleteItem() {
        return _deleteItem;
    }

    public static boolean get_editItem() {
        return _editItem;
    }

    public static boolean get_editOrder() {
        return _editOrder;
    }

    public static boolean get_exportDB() {
        return _exportDB;
    }

    public static String get_loggedInUser() {
        return _loggedInUser;
    }

    public static boolean get_newOrder() {
        return _newOrder;
    }

    public static boolean get_reports() {
        return _reports;
    }

    public static boolean get_reprintOrder() {
        return _reprintOrder;
    }

    public static boolean get_updateSetting() {
        return _updateSetting;
    }

    public static void set_accessRights(boolean bl) {
        _accessRights = bl;
    }

    public static void set_addItem(boolean bl) {
        _addItem = bl;
    }

    public static void set_changePassword(boolean bl) {
        _changePassword = bl;
    }

    public static void set_dayEnd(boolean bl) {
        _dayEnd = bl;
    }

    public static void set_debitAmount(boolean bl) {
        _debitAmount = bl;
    }

    public static void set_deleteItem(boolean bl) {
        _deleteItem = bl;
    }

    public static void set_editItem(boolean bl) {
        _editItem = bl;
    }

    public static void set_editOrder(boolean bl) {
        _editOrder = bl;
    }

    public static void set_exportDB(boolean bl) {
        _exportDB = bl;
    }

    public static void set_loggedInUser(String string2) {
        _loggedInUser = string2;
    }

    public static void set_newOrder(boolean bl) {
        _newOrder = bl;
    }

    public static void set_reports(boolean bl) {
        _reports = bl;
    }

    public static void set_reprintOrder(boolean bl) {
        _reprintOrder = bl;
    }

    public static void set_updateSetting(boolean bl) {
        _updateSetting = bl;
    }
}

