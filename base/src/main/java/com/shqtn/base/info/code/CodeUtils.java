package com.shqtn.base.info.code;

/**
 * Created by android on 2017/7/14.
 */

public class CodeUtils {
    static {
        codeTag = new CodeTag();
    }

    public static CodeTag codeTag;

    public static class Builder {

        public static void setCodeTag(CodeTag tag) {
            codeTag = tag;
        }
    }


    public static boolean isGoods(AllotBean code) {
        if (code == null || codeTag == null || codeTag.getGoods() == null) {
            return false;
        }

        if (codeTag.getGoods().endsWith(code.getDOC_TYPE())) {
            return true;
        }

        return false;
    }

    public static boolean isManifest(AllotBean code) {
        if (code == null || codeTag == null || codeTag.getManifest() == null) {
            return false;
        }

        if (codeTag.getManifest().endsWith(code.getDOC_TYPE())) {
            return true;
        }
        return false;
    }

    public static boolean isLpn(AllotBean code) {
        if (code == null || codeTag == null || codeTag.getLpn() == null) {
            return false;
        }

        if (codeTag.getLpn().endsWith(code.getDOC_TYPE())) {
            return true;
        }
        return false;
    }

    public static boolean isRack(AllotBean code) {
        if (code == null || codeTag == null || codeTag.getRack() == null) {
            return false;
        }

        if (codeTag.getRack().endsWith(code.getDOC_TYPE())) {
            return true;
        }
        return false;
    }

    public static CodeGoods getGoods(AllotBean code) {
        if (code != null && code.getCODE_LIST() != null && code.getCODE_LIST().size() > 0)
            return code.getCODE_LIST().get(0);
        return null;
    }

    public static CodeManifest getManifest(AllotBean code) {
        String manifest = code.getDOC_CODE();
        CodeManifest codeManifest = new CodeManifest();
        codeManifest.setDocNo(manifest);
        return codeManifest;
    }

    public static CodeLpn getLpn(AllotBean code) {
        CodeLpn codeLpn = new CodeLpn();
        String lpnNo = code.getDOC_CODE();
        codeLpn.setLpnNo(lpnNo);
        codeLpn.setGoodsList(code.getCODE_LIST());
        return codeLpn;
    }

    public static CodeRack getRack(AllotBean code) {
        String rackNo = code.getDOC_CODE();
        CodeRack codeRack = new CodeRack();
        codeRack.setRackNo(rackNo);
        return codeRack;
    }
}
