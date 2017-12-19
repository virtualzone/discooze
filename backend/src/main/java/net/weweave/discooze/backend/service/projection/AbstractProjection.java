package net.weweave.discooze.backend.service.projection;

import java.util.Date;
import java.util.UUID;

public abstract interface AbstractProjection {
    UUID getId();
    Date getCreated();
    Date getLastModified();
}
