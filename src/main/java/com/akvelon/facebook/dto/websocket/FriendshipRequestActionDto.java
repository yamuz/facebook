package com.akvelon.facebook.dto.websocket;

import com.akvelon.facebook.enumr.FriendshipActionEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendshipRequestActionDto {
    private FriendshipActionEnum action;
    private Long requestFromUserId;
    private Long requestToUserId;
}