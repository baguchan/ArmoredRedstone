package baguchan.armored_redstone.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class FireArmorAnimation {

	public static final AnimationDefinition ATTACKFINISH = AnimationDefinition.Builder.withLength(0.24f).addAnimation("LeftHand", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.08f, KeyframeAnimations.degreeVec(-45f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).addAnimation("RightHand", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.08f, KeyframeAnimations.degreeVec(-45f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
	public static final AnimationDefinition ATTACK = AnimationDefinition.Builder.withLength(0.24f).addAnimation("LeftHand", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-45f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).addAnimation("RightHand", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-45f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
}
