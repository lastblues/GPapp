package com.example.andyshin.gpapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Andy Shin on 4/5/2017.
 */

public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeAdapter.YouTubeViewHolder> {

    private final int UNINITIALIZED = 1;
    private final int INITIALIZING = 2;
    private final int INITIALIZED = 3;
    private int blackColor = Color.parseColor("#FF000000");
    private int transparentColor = Color.parseColor("#00000000");

    private List<YouTubeItem> yItems;
    private Context mContext;

    @Override
    public YouTubeAdapter.YouTubeViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater =  LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.youtube_item, parent, false);
        YouTubeViewHolder viewHolder = new YouTubeViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(YouTubeAdapter.YouTubeViewHolder holder, int position){
        final YouTubeItem item = yItems.get(position);
        final YouTubeViewHolder videoViewHolder = (YouTubeViewHolder) holder;
        //videoViewHolder.tvTitle.setText(item.);
        videoViewHolder.ivYtLogo.setVisibility(View.VISIBLE);
        videoViewHolder.ytThumbnailView.setTag(R.id.videoid, item);
        videoViewHolder.ivYtLogo.setBackgroundColor(blackColor);

        int state = (int) videoViewHolder.ytThumbnailView.getTag(R.id.initialize);

        if(state == UNINITIALIZED){
            videoViewHolder.initialize();
        }
        else if(state == INITIALIZED) {
            YouTubeThumbnailLoader loader = (YouTubeThumbnailLoader) videoViewHolder.ytThumbnailView.getTag(R.id.thumbnailloader);
            //loader.setVideo(item.);

        }

    }

    @Override
    public int getItemCount() {
        return yItems.size();
    }

    public class YouTubeViewHolder extends RecyclerView.ViewHolder {
        public YouTubeThumbnailView ytThumbnailView = null;
        public ImageView ivYtLogo = null;
        public TextView tvTitle = null;

        public YouTubeViewHolder(View itemView) {
            super(itemView);
            ytThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.yt_thumbnail);
            ivYtLogo = (ImageView) itemView.findViewById(R.id.iv_yt_logo);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);

            initialize();
        }

        public void initialize() {
            ivYtLogo.setBackgroundColor(blackColor);
            ytThumbnailView.setTag(R.id.initialize, INITIALIZING);
            ytThumbnailView.setTag(R.id.thumbnailloader, null);
            ytThumbnailView.setTag(R.id.videoid, "");

            ytThumbnailView.initialize(Config.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    ytThumbnailView.setTag(R.id.initialize, INITIALIZED);
                    ytThumbnailView.setTag(R.id.thumbnailloader, youTubeThumbnailLoader);

                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String loadedVideoId) {
                            String currentVideoId = (String) ytThumbnailView.getTag(R.id.videoid);

                            //DOES THIS CODE ONLY EXIST TO CHANGE THE COLORS!!!?!!?!!?!
                            if (currentVideoId.equals(loadedVideoId)) {
                                ivYtLogo.setBackgroundColor(transparentColor);
                            } else {
                                ivYtLogo.setBackgroundColor(blackColor);
                            }
                        }

                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                            ivYtLogo.setBackgroundColor(blackColor);
                        }
                    });

                    String videoId = (String) ytThumbnailView.getTag(R.id.videoid);
                    if (videoId != null && !videoId.isEmpty()) {
                        youTubeThumbnailLoader.setVideo(videoId);
                    }
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                    ytThumbnailView.setTag(R.id.initialize, UNINITIALIZED);
                    ivYtLogo.setBackgroundColor(blackColor);
                }
            });
        }
    }
}


