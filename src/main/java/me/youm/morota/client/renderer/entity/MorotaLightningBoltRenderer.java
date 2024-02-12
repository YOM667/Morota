package me.youm.morota.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import me.youm.morota.world.entity.MorotaLightningBolt;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class MorotaLightningBoltRenderer extends EntityRenderer<MorotaLightningBolt> {
   public MorotaLightningBoltRenderer(EntityRendererProvider.Context pContext) {
      super(pContext);
   }

   public void render(MorotaLightningBolt pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
      float[] afloat = new float[8];
      float[] afloat1 = new float[8];
      float f = 0.0F;
      float f1 = 0.0F;
      Random random = new Random(pEntity.seed);

      for (int i = 7; i >= 0; --i) {
         afloat[i] = f;
         afloat1[i] = f1;
         f += (float) (random.nextInt(11) - 5);
         f1 += (float) (random.nextInt(11) - 5);
      }

      VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.lightning());
      Matrix4f matrix4f = pMatrixStack.last().pose();

      for (int j = 0; j < 4; ++j) {
         Random random1 = new Random(pEntity.seed);

         for (int k = 0; k < 3; ++k) {
            int l = 7;
            int i1 = 0;
            if (k > 0) {
               l = 7 - k;
            }

            if (k > 0) {
               i1 = l - 2;
            }

            float f2 = afloat[l] - f;
            float f3 = afloat1[l] - f1;

            for (int j1 = l; j1 >= i1; --j1) {
               float f4 = f2;
               float f5 = f3;
               if (k == 0) {
                  f2 += (float) (random1.nextInt(11) - 5);
                  f3 += (float) (random1.nextInt(11) - 5);
               } else {
                  f2 += (float) (random1.nextInt(31) - 15);
                  f3 += (float) (random1.nextInt(31) - 15);
               }

               float f10 = 0.1F + (float) j * 0.2F;
               if (k == 0) {
                  f10 *= (float) j1 * 0.1F + 1.0F;
               }

               float f11 = 0.1F + (float) j * 0.2F;
               if (k == 0) {
                  f11 *= ((float) j1 - 1.0F) * 0.1F + 1.0F;
               }

               quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, 0.44F, 0.45F, 1F, f10, f11, false, false, true, false);
               quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, 0.44F, 0.45F, 1F, f10, f11, true, false, true, true);
               quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, 0.44F, 0.45F, 1F, f10, f11, true, true, false, true);
               quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, 0.44F, 0.45F, 1F, f10, f11, false, true, false, false);
            }
         }
      }

   }
   /**
    * Returns the location of an entity's texture.
    */
   @NotNull
   @Override
   public ResourceLocation getTextureLocation(@NotNull MorotaLightningBolt pEntity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }

   private static void quad(Matrix4f matrix4f, VertexConsumer vertexConsumer, float p_115275_, float p_115276_, int p_115277_, float p_115278_, float p_115279_, float red, float green, float blue, float p_115283_, float p_115284_, boolean p_115285_, boolean p_115286_, boolean p_115287_, boolean p_115288_) {
      vertexConsumer.vertex(matrix4f, p_115275_ + (p_115285_ ? p_115284_ : -p_115284_), (float) (p_115277_ * 16), p_115276_ + (p_115286_ ? p_115284_ : -p_115284_)).color(red, green, blue, 0.5F).endVertex();
      vertexConsumer.vertex(matrix4f, p_115278_ + (p_115285_ ? p_115283_ : -p_115283_), (float) ((p_115277_ + 1) * 16), p_115279_ + (p_115286_ ? p_115283_ : -p_115283_)).color(red, green, blue, 0.5F).endVertex();
      vertexConsumer.vertex(matrix4f, p_115278_ + (p_115287_ ? p_115283_ : -p_115283_), (float) ((p_115277_ + 1) * 16), p_115279_ + (p_115288_ ? p_115283_ : -p_115283_)).color(red, green, blue, 0.5F).endVertex();
      vertexConsumer.vertex(matrix4f, p_115275_ + (p_115287_ ? p_115284_ : -p_115284_), (float) (p_115277_ * 16), p_115276_ + (p_115288_ ? p_115284_ : -p_115284_)).color(red, green, blue, 0.5F).endVertex();
   }

}
