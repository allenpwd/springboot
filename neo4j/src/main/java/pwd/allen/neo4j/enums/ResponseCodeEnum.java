package pwd.allen.neo4j.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pwd.allen.neo4j.exception.BaseExceptionInterface;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    //通用异常状态码
    SYSTEM_ERROR("10000", "出错啦，后台小哥正在努力修复中..."),
    //业务异常状态码
    PRODUCT_NOT_FOUND("20000", "该产品不存在（测试使用）"),
    ARTICLE_REL_CATEGORY_ERROR("10001","文章关联分类数据库有误,请练习管理员及时排查"),
    ARTICLE_REL_TAG_ERROR("10001","文章关联分类数据库有误,请练习管理员及时排查"),
    //参数校验异常码
    PARAM_NOT_VALID("10001", "参数错误"),
    LOGIN_FAIL("20000", "登录失败"),
    USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),
    UNAUTHORIZED("20002", "无访问权限，请先登录！"),
    USERNAME_NOT_FOUND("20003", "该用户不存在"),
    CATEGORY_NAME_IS_EXISTED("20005", "该分类已存在，请勿重复添加！"),
    TAG_CANT_DUPLICATE("20006", "请勿添加表中已存在的标签"),
    TAG_NOT_EXISTED("20007", "该标签不存在"),
    FILE_UPLOAD_FAILED("20008", "文件上传失败！"),
    CATEGORY_NOT_EXISTED("20009","提交的分类不存在"),
    ARTICLE_NOT_FOUND("20010","文章不存在"),
    CATEGORY_CAN_NOT_DEL("20011","该分类下存在文章，请先删除对应文章后在进行删除"),
    TAG_CAN_NOT_DEL("20012","该标签下存在文章，请先删除对应文章后在进行删除"),
    ADD_SONG_ERROR("10001","创建歌曲节点失败" ),
    DEL_SONG_ERROR("10002","删除歌曲节点失败" ),
    UPDATE_SONG_ERROR("10004","更新歌曲失败" ),
    DETAIL_SONG_ERROR("10003","歌曲为空" ),
    ADD_ALBUM_ERROR("10005", "创建专辑节点失败"),
    ALBUM_IS_EXITS("10006", "专辑已存在"),
    ALBUM_CONTAIN_SONG("10007","专辑内包含歌曲，无法删除" ),
    DELETE_ALBUM_ERROR("10008","删除专辑节点失败" ),
    DETAIL_ALBUM_ERROR("10009","专辑为空" ),
    UPDATE_ALBUM_ERROR("10010","更新专辑失败"),
    ADD_ARTIST_ERROR("10011", "添加歌手失败"),
    ARTIST_CONTAIN_ALBUM("10012","歌手下还有专辑，无法删除"),
    DELETE_ARTIST_ERROR("10013","删除歌手节点失败" ),
    DETAIL_ARTIST_ERROR("10014", "歌手不存在"),
    UPDATE_ARTIST_ERROR("10015","更新歌手失败" );

    // 异常码
    private String errorCode;
    // 错误信息
    private String errorMessage;
}
