package com.example.asyncmail.endpoint.event.consumer.model;

import com.example.asyncmail.PojaGenerated;
import com.example.asyncmail.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
