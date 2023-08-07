package com.feature.home

import com.example.domain.model.Crypto

enum class TestResponseEnum {
    ERROR, SUCCESS
}

val cryptoCurrencies = listOf(
    Crypto("01coin", "zoc", "01coin"),
    Crypto("0chain", "zcn", "Zus"),
    Crypto("0vix-protocol", "vix", "0VIX Protocol"),
    Crypto("0x", "zrx", "0x Protocol"),
    Crypto("0x0-ai-ai-smart-contract", "0x0", "0x0.ai: AI Smart Contract"),
    Crypto("0x1-tools-ai-multi-tool", "0x1", "0x1.tools: AI Multi-tool"),
    Crypto("0xauto-io-contract-auto-deployer", "0xa", "0xAuto.io : Contract Auto Deployer"),
    Crypto("0xcoco", "coco", "0xCoco")
)