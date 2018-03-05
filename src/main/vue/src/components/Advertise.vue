<template>
  <div id="advertise">
    <div class="uk-position-relative uk-visible-toggle uk-light"
         uk-slideshow="min-height: 200; max-height: 250;autoplay: true">
      <ul class="uk-slideshow-items">
        <li>
          <img src="../assets/1.jpg" alt="" uk-cover>
        </li>
        <li>
          <img src="../assets/2.jpg" alt="" uk-cover>
        </li>
        <li>
          <img src="../assets/3.jpg" alt="" uk-cover>
        </li>
      </ul>

      <a class="uk-position-center-left uk-position-small uk-hidden-hover" href="#" uk-slidenav-previous
         uk-slideshow-item="previous"></a>
      <a class="uk-position-center-right uk-position-small uk-hidden-hover" href="#" uk-slidenav-next
         uk-slideshow-item="next"></a>
    </div>
  </div>

</template>

<script>
  export default {
    name: 'Advertise',
    data() {
      return {
        msg: 'Welcome to Ironman\'s world haha !'
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
