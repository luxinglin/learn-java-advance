package cn.com.gary.model.constants;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2018-04-24 11:33
 **/
public interface MessageConstants {
    /**
     * 字段校验常量
     */
    String FIELD_VALID_NOT_NULL = "field.valid.not.null";
    String FIELD_VALID_MIN_LEN = "field.valid.min.len";
    String FIELD_VALID_MIN = "field.valid.min";
    String FIELD_VALID_MAX_LEN = "field.valid.max.len";
    String FIELD_VALID_SIZE = "field.valid.size";
    String FIELD_VALID_MAX = "field.valid.max";
    String FIELD_VALID_EMAIL = "field.valid.email";
    String FIELD_VALID_IP = "field.valid.ip";
    String FIELD_VALID_MOBILE = "field.valid.mobile";
    /**
     * 新增对象相关的文本提示
     */
    String INSERT_SUCCESS = "insert.success";
    String INSERT_SUCCESS_PARAM = "insert.success.param";
    String INSERT_NULL = "insert.null.error";
    String INSERT_REQUIRED_ERROR = "insert.required.error";
    String INSERT_FILED_NOT_VALID_ERROR = "insert.field.not.valid.error";
    String INSERT_FILED_NOT_VALID_ERROR_PARAM = "insert.field.not.valid.error.param";
    String INSERT_EXCEPTION = "insert.exception";
    String INSERT_DUPLICATE_ERROR = "insert.duplicate.error";

    /**
     * 更新对象相关的文本提示
     */
    String UPDATE_SUCCESS = "update.success";
    String UPDATE_SUCCESS_PARAM = "update.success.param";
    String UPDATE_NULL = "update.null.error";
    String UPDATE_BY_KEY_BUT_NULL = "update.by.key.but.null";
    String UPDATE_ITEM_DOT_NOT_EXIST = "update.item.not.exist";
    String UPDATE_REQUIRED_ERROR = "update.required.error";
    String UPDATE_DUPLICATE_ERROR = "update.duplicate.error";
    String UPDATE_FILED_NOT_VALID_ERROR = "update.field.not.valid.error";
    String UPDATE_FILED_NOT_VALID_ERROR_PARAM = "update.field.not.valid.error.param";

    /**
     * 保存对象字段必填
     */
    String SAVE_OR_UPDATE_REQUIRED_ERROR = "save.or.update.required.error";
    /**
     * 查询对象相关的文本提示
     */
    String SELECT_BY_KEY_BUT_NULL = "select.by.key.but.null";
    String SELECT_BY_KEY_NOT_EXIST = "select.by.key.not.exist";
    String SELECT_BY_KEY_NOT_EXIST_PARAM = "select.by.key.not.exist.param";
    /**
     * 删除对象相关的文本提示
     */
    String DELETE_SUCCESS = "delete.success";
    String DELETE_SUCCESS_PARAM = "delete.success.param";
    String DELETE_BY_KEY_BUT_NULL = "delete.by.key.but.null";
    String DELETE_NULL = "delete.null";
    String DELETE_ITEM_HAS_CHILDREN = "delete.item.has.children";
    String DELETE_ITEM_HAS_RELATION = "delete.item.has.relation";
    String DELETE_ITEM_NOT_EXIST = "delete.item.not.exist";
    String DELETE_ITEM_SYSTEM_DEFINED = "delete.item.system.defined";

}
