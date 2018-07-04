package com.ndmsystems.api.models;

/**
 * Created by bas on 07.02.17.
 */

public class PeerInfo {
    public final String cid;
    public final String name;
    public final String type;

    public PeerInfo(String cid, String name, String type) {
        this.cid = cid;
        this.name = name;
        this.type = type;
    }
}
