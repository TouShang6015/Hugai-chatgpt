package com.hugai.core.midjourney.listener;

import com.hugai.core.midjourney.common.entity.DiscordAccount;
import com.hugai.core.midjourney.pool.DiscordSocketAccountPool;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.internal.utils.compress.Decompressor;
import net.dv8tion.jda.internal.utils.compress.ZlibDecompressor;
import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;

/**
 * discord socket 消息接收处理
 *
 * @author WuHao
 * @since https://github.com/novicezk/midjourney-proxy
 */
@Slf4j
public class DiscordSocketListener extends DiscordSocketCommonListener {

    private final Decompressor decompressor;

    public DiscordSocketListener(DiscordAccount discordAccount) {
        super(discordAccount);
        this.decompressor = new ZlibDecompressor(2048);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        this.socket = webSocket;
        log.info("[Discord] - 账号：{} 已连接", this.discordAccount.getUserName());
        DiscordSocketAccountPool.add(this.discordAccount.getUserName(), this.discordAccount, this.socket);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        if (this.decompressor == null) {
            return;
        }
        byte[] binary = bytes.toByteArray();
        byte[] decompressBinary;
        try {
            decompressBinary = this.decompressor.decompress(binary);
        } catch (DataFormatException e) {
            log.error("binary异常:{}", binary);
            e.printStackTrace();
            return;
        }
        if (decompressBinary == null) {
            return;
        }
        String json = new String(decompressBinary, StandardCharsets.UTF_8);
        DataObject data = DataObject.fromJson(json);
        super.handleMessage(data);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        t.printStackTrace();
        log.info("[Discord socket failure] - 账号：{} 异常：{}", this.discordAccount.getUserName(), t.getMessage());
        this.cancel();
        DiscordSocketAccountPool.remove(this.discordAccount.getUserName());
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        log.info("[Discord socket Closed] - 账号：{} 关闭连接   code:{} reason: {}", this.discordAccount.getUserName(), code, reason);
        this.cancel();
        DiscordSocketAccountPool.remove(this.discordAccount.getUserName());
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        log.info("[Discord socket Closing] - 账号：{} 关闭连接   code:{} reason: {}", this.discordAccount.getUserName(), code, reason);
        this.cancel();
        DiscordSocketAccountPool.remove(this.discordAccount.getUserName());
    }

}
