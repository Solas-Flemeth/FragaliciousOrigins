package org.originsreborn.fragaliciousorigins.jdbc;

import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;


public class SerializedOrigin {
    private String uuid; //PK
    private String originType;
    private String originState; //PK
    private String originData;

    public SerializedOrigin(String uuid, String originType, String originState, String originData) {
        this.uuid = uuid;
        this.originType = originType;
        this.originState = originState;
        this.originData = originData;
    }

    public SerializedOrigin(Origin origin){
        uuid = origin.getUUID().toString();
        originType = origin.getType().getDisplay();
        originState = origin.getState().toString();
        originData = origin.serializeCustomData();
    }

    public Origin deserializeOrigin(){
        OriginType type = OriginType.getByDisplayName(originType);
        System.out.println(this.toString());
        return type.generateOrigin(uuid, originState, originData);
    }

    public String getUuid() {
        return uuid;
    }

    public String getOriginType() {
        return originType;
    }

    public String getOriginState() {
        return originState;
    }

    public String getOriginData() {
        return originData;
    }

    @Override
    public String toString() {
        return "SerializedOrigin{" +
                "uuid='" + uuid + '\'' +
                ", originType='" + originType + '\'' +
                ", originState='" + originState + '\'' +
                ", originData='" + originData + '\'' +
                '}';
    }
}
