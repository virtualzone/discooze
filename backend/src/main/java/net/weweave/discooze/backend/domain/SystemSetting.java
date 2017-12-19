package net.weweave.discooze.backend.domain;

import javax.persistence.*;

@Entity
public class SystemSetting {
    @Id
    @Enumerated(EnumType.STRING)
    private SystemSettingKey key;

    private String value;

    public SystemSetting() {
    }

    public SystemSetting(SystemSettingKey key) {
        this.setKey(key);
    }

    public SystemSettingKey getKey() {
        return key;
    }

    public void setKey(SystemSettingKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
