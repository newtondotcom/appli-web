<script setup lang="ts">
import { Notebook, Star } from "lucide-vue-next";
import { ref } from "vue";
import { useToast } from "@/components/ui/toast/use-toast";
const { toast } = useToast();
const props = defineProps({
  id_util: {
    type: Number,
    required: true,
  },
  id_event: {
    type: Number,
    required: true,
  },
  tabAvis: {
    type: Array,
    required: true,
  },
});
const isStar1 = ref(false);
const isStar2 = ref(false);
const isStar3 = ref(false);
const isStar4 = ref(false);
const isStar5 = ref(false);
const handleStar1 = () => {
  if (isStar1.value) {
    isStar1.value = false;
    isStar2.value = false;
    isStar3.value = false;
    isStar4.value = false;
    isStar5.value = false;
  } else {
    isStar1.value = true;
  }
};
const handleStar2 = () => {
  if (isStar2.value) {
    isStar5.value = false;
    isStar4.value = false;
    isStar3.value = false;
    isStar2.value = false;
  } else {
    isStar2.value = true;
    isStar1.value = true;
  }
};
const handleStar3 = () => {
  if (isStar3.value) {
    isStar3.value = false;
    isStar4.value = false;
    isStar5.value = false;
  } else {
    isStar3.value = true;
    isStar2.value = true;
    isStar1.value = true;
  }
};
const handleStar4 = () => {
  if (isStar4.value) {
    isStar4.value = false;
    isStar5.value = false;
  } else {
    isStar4.value = true;
    isStar3.value = true;
    isStar2.value = true;
    isStar1.value = true;
  }
};
const handleStar5 = () => {
  if (isStar5.value) {
    isStar5.value = false;
  } else {
    isStar5.value = true;
    isStar4.value = true;
    isStar3.value = true;
    isStar2.value = true;
    isStar1.value = true;
  }
};
const titre = ref("");
const commentaire = ref("");
async function sendAvis() {
  let note = 0;
  if (isStar5.value) {
    note = 5;
  } else if (isStar4.value) {
    note = 4;
  } else if (isStar3.value) {
    note = 3;
  } else if (isStar2.value) {
    note = 2;
  } else if (isStar1.value) {
    note = 1;
  } else {
    note = 0;
  }
  if (commentaire.value === "" || titre.value === "") {
    alert("Veuillez remplir tous les champs");
    return;
  } else {
    try {
      const data = await $fetch(
        `http://localhost:8080/PasserellePro/Serv?op=creer_avis`,
        {
          method: "POST",
          credentials: "include",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            titre: titre.value,
            note: note,
            contenu: commentaire.value,
            id_util: props.id_util,
            id_event: props.id_event,
          }),
        }
      );
      const avis = {
        titre: titre.value,
        note: note,
        contenu: commentaire.value,
        id: Math.random(),
      };
      props.tabAvis.push(avis);
      toast({
        title: "Succès",
        description: "Votre avis a bien été envoyé",
      });
      titre.value = "";
      commentaire.value = "";
      isStar1.value = false;
      isStar2.value = false;
      isStar3.value = false;
      isStar4.value = false;
      isStar5.value = false;
    } catch (error) {
      console.error(error);
    }
  }
}
</script>

<template>
  <Card class="w-[700px] h-[300px]">
    <CardHeader>
      <CardTitle>
        <Input type="text" v-model="titre" placeholder="Titre" class="w-[40%] h-1" />
      </CardTitle>
    </CardHeader>
    <CardContent class="p-0 px-6 h-[50%]">
      <Textarea v-model="commentaire" placeholder="Commentaire" class="w-full h-[70%]" />
    </CardContent>
    <CardFooter class="flex px-6 justify-between">
      <div class="flex flex-row">
      <div @click="handleStar1">
        <Star
          class="ml-1 cursor-pointer"
          :size="25"
          :color="isStar1 ? '#000000' : '#d6d6d6'"
        />
      </div>
      <div @click="handleStar2">
        <Star
          class="ml-1 cursor-pointer"
          :size="25"
          :color="isStar2 ? '#000000' : '#d6d6d6'"
        />
      </div>
      <div @click="handleStar3">
        <Star
          class="ml-1 cursor-pointer"
          :size="25"
          :color="isStar3 ? '#000000' : '#d6d6d6'"
        />
      </div>
      <div @click="handleStar4">
        <Star
          class="ml-1 cursor-pointer"
          :size="25"
          :color="isStar4 ? '#000000' : '#d6d6d6'"
        />
      </div>
      <div @click="handleStar5">
        <Star
          class="ml-1 cursor-pointer"
          :size="25"
          :color="isStar5 ? '#000000' : '#d6d6d6'"
        />
      </div>
    </div>
      <Button @click="sendAvis">
        Envoyer
      </Button>
    </CardFooter>
  </Card>
</template>
