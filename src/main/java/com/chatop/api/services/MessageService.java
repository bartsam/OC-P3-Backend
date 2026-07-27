package com.chatop.api.services;

import com.chatop.api.dto.MessageRequestDto;
public interface MessageService {

void createMessage(MessageRequestDto messageRequestDto, Integer userId);

}

  