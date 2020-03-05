package com.yangshikun.mvvmdemo.utils.binding;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.BindingAdapter;

import com.blankj.utilcode.util.ImageUtils;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.yangshikun.mvvmdemo.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;

/**
 * Created by yang.shikun on 2020/3/5 13:32
 */

public class BindingUtils {
    //防重复点击间隔(秒)
    public static final int CLICK_INTERVAL = 1;

    /**
     * View的onClick事件绑定
     * isThrottleFirst 是否开启防止过快点击
     */
    @BindingAdapter(value = {"onClickCommand", "isThrottleFirst", "clickData"}, requireAll = false)
    public static <T> void onClickCommand(View view, final BindingCommand clickCommand,
                                          final boolean isThrottleFirst, T data) {
        if (isThrottleFirst) {
            RxView.clicks(view).subscribe((Consumer<Object>) object -> {
                if (clickCommand != null) {
                    if (data != null) {
                        clickCommand.execute(data);
                    } else {
                        clickCommand.execute();
                    }
                }
            });
        } else {
            RxView.clicks(view).throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                    .subscribe((Consumer<Object>) object -> {
                        if (clickCommand != null) {
                            if (data != null) {
                                clickCommand.execute(data);
                            } else {
                                clickCommand.execute();
                            }
                        }
                    });
        }
    }

    /**
     * view的onLongClick事件绑定
     */
    @BindingAdapter(value = {"onLongClickCommand", "T"}, requireAll = false)
    public static <T> void onLongClickCommand(View view, final BindingCommand clickCommand, T t) {
        RxView.longClicks(view).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object object) throws Exception {
                if (clickCommand != null) {
                    if (t != null) {
                        clickCommand.execute(t);
                    } else {
                        clickCommand.execute();
                    }
                }
            }
        });
    }


    @BindingAdapter(value = {"onSwitchChangeCommand"}, requireAll = false)
    public static void onSwitchChangeCommand(SwitchCompat view, final BindingCommand<Boolean> switchCommand) {
        view.setOnCheckedChangeListener((v, check) -> {
            switchCommand.execute(check);
        });
    }

    @BindingAdapter(value = {"bgcolor"}, requireAll = false)
    public static void setBgColor(View view, int colorId) {
        view.setBackgroundResource(colorId);
    }

    @BindingAdapter(value = {"textColor", "hintColor"}, requireAll = false)
    public static void setTextColor(TextView view, int colorId, int hintColor) {
        if (colorId != 0) {
            view.setTextColor(view.getResources().getColor(colorId));
        }
        if (hintColor != 0) {
            view.setHintTextColor(view.getResources().getColor(hintColor));
        }
    }

    @BindingAdapter(value = {"viewVisible", "showAnim", "hideAnim"}, requireAll = false)
    public static void setVisiable(View view, boolean visible, int showAnim, int hideAnim) {
        if (visible && view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            if (showAnim != 0) {
                view.setAnimation(AnimationUtils.loadAnimation(view.getContext(), showAnim));
            }
        }
        if (!visible && view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
            if (hideAnim != 0) {
                view.setAnimation(AnimationUtils.loadAnimation(view.getContext(), hideAnim));
            }
        }

    }

    @BindingAdapter(value = {"imgSrc"}, requireAll = false)
    public static void setImageRes(ImageView view, int imgRes) {
        view.setImageResource(imgRes);
    }

    @BindingAdapter(value = {"imgDrawable"}, requireAll = false)
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
    }

    @BindingAdapter(value = {"shake"}, requireAll = false)
    public static void shakeView(View view, boolean shake) {
        if (shake) {
            RotateAnimation animation = new RotateAnimation(-3, 3, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(60);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setRepeatCount(6);
            view.startAnimation(animation);
        }
    }

    @BindingAdapter(value = {"viewBackground"}, requireAll = false)
    public static void setImageRes(View view, int res) {
        view.setBackground(view.getResources().getDrawable(res));
    }

    @BindingAdapter(value = {"backgroundDrawable"}, requireAll = false)
    public static void setbgImg(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

    @BindingAdapter(value = {"bgRes"}, requireAll = false)
    public static void setBg(View view, int bgRes) {
        view.setBackgroundResource(bgRes);
    }

    @BindingAdapter(value = {"onTextChangedCommand"}, requireAll = false)
    public static void onTextChanged(TextView textView, BindingCommand<String> command) {
        RxTextView.textChanges(textView).subscribe(charSequence -> {
            if (command != null) {
                command.execute(charSequence.toString());
            }
        });
    }

    @BindingAdapter(value = {"onFocusChangedCommand"})
    public static void onFocusChanged(View view, BindingCommand<Boolean> command) {
        RxView.focusChanges(view).subscribe(aBoolean -> {
            if (command != null) {
                command.execute(aBoolean);
            }
        });
    }

//    @BindingAdapter(value = {"itemLayoutId", "items", "flowViewModel", "maxHeight", "itemBottomMargin"}, requireAll = false)
//    public static <T, VM> void setFlowDatas(FlowLayout layout, int layoutId, List<T> list, VM viewModel, int dimen, int bottomMarginDimen) {
//        layout.removeAllViews();
//        if (dimen != 0) {
//            layout.setMaxHeight((int) layout.getResources().getDimension(dimen));
//        }
//        for (T t : list) {
//            ViewDataBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(layout.getContext()), layoutId, null, false);
//            itemBinding.setVariable(BR.item, t);
//            if (viewModel != null) {
//                itemBinding.setVariable(BR.viewmodel, viewModel);
//            }
//            if (bottomMarginDimen != 0) {
//                layout.add(itemBinding.getRoot(), bottomMarginDimen);
//            } else {
//                layout.add(itemBinding.getRoot());
//            }
//        }
//    }

    @BindingAdapter(value = {"drawLeft"})
    public static void setDrawableLeft(TextView view, Drawable drawable) {
        if (drawable != null) {
            int height = (int) (view.getMeasuredHeight() * 0.8);
            double scale = view.getMeasuredHeight() * 0.8 / drawable.getIntrinsicHeight();
            int width = (int) (drawable.getIntrinsicWidth() * scale);
            drawable.setBounds(0, 0, width, height);
        }
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawablePadding((int) view.getResources().getDimension(R.dimen.qb_px_3));
        view.setCompoundDrawables(drawable, drawables[1], drawables[2], drawables[3]);
    }

    @BindingAdapter(value = {"bgBitmap"})
    public static void setBgBitmap(View view, Bitmap bitmap) {
        view.setBackground(ImageUtils.bitmap2Drawable(bitmap));
    }

    @BindingAdapter(value = {"viewAlpha"})
    public static void setViewAlpha(View view, int alpha) {
        view.setVisibility(alpha == 0 ? View.GONE : View.VISIBLE);
        view.setAlpha((float) alpha / 255);
    }

    @BindingAdapter(value = {"clickView", "clickData"}, requireAll = false)
    public static <T> void clickView(View view, ClickListener<T> listener, T data) {
        Disposable subscribe = RxView.clicks(view).subscribe(new Consumer<Unit>() {
            @Override
            public void accept(Unit unit) throws Exception {
                if (listener != null) {
                    listener.onClick(view, data);
                }
            }
        });
    }

    public interface ClickListener<T> {
        void onClick(View view, T data);
    }

    @BindingAdapter(value = {"inputType"})
    public static void setInputType(TextView textView, int type) {
        textView.setInputType(type);
    }

}

