<script>
export default {
  name: "Home"
}
</script>

<template>
  <div id="home">
    <div class="wrapper">
      <div class="left-side">
        <svg viewBox="0 0 512 512" fill="currentColor" xmlns="http://www.w3.org/2000/svg" class="active">
          <path d="M197.3 170.7h-160A37.4 37.4 0 010 133.3v-96A37.4 37.4 0 0137.3 0h160a37.4 37.4 0 0137.4 37.3v96a37.4 37.4 0 01-37.4 37.4zM37.3 32c-3 0-5.3 2.4-5.3 5.3v96c0 3 2.4 5.4 5.3 5.4h160c3 0 5.4-2.4 5.4-5.4v-96c0-3-2.4-5.3-5.4-5.3zm0 0M197.3 512h-160A37.4 37.4 0 010 474.7v-224a37.4 37.4 0 0137.3-37.4h160a37.4 37.4 0 0137.4 37.4v224a37.4 37.4 0 01-37.4 37.3zm-160-266.7c-3 0-5.3 2.4-5.3 5.4v224c0 3 2.4 5.3 5.3 5.3h160c3 0 5.4-2.4 5.4-5.3v-224c0-3-2.4-5.4-5.4-5.4zm0 0M474.7 512h-160a37.4 37.4 0 01-37.4-37.3v-96a37.4 37.4 0 0137.4-37.4h160a37.4 37.4 0 0137.3 37.4v96a37.4 37.4 0 01-37.3 37.3zm-160-138.7c-3 0-5.4 2.4-5.4 5.4v96c0 3 2.4 5.3 5.4 5.3h160c3 0 5.3-2.4 5.3-5.3v-96c0-3-2.4-5.4-5.3-5.4zm0 0M474.7 298.7h-160a37.4 37.4 0 01-37.4-37.4v-224A37.4 37.4 0 01314.7 0h160A37.4 37.4 0 01512 37.3v224a37.4 37.4 0 01-37.3 37.4zM314.7 32c-3 0-5.4 2.4-5.4 5.3v224c0 3 2.4 5.4 5.4 5.4h160c3 0 5.3-2.4 5.3-5.4v-224c0-3-2.4-5.3-5.3-5.3zm0 0" /></svg>
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
          <path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5" /></svg>
        <svg viewBox="0 1 511 512" fill="currentColor">
          <path d="M498.7 222.7L289.8 13.8a46.8 46.8 0 00-66.7 0L14.4 222.6l-.2.2A47.2 47.2 0 0047 303h8.3v153.7a55.2 55.2 0 0055.2 55.2h81.7a15 15 0 0015-15V376.5a25.2 25.2 0 0125.2-25.2h48.2a25.2 25.2 0 0125.1 25.2V497a15 15 0 0015 15h81.8a55.2 55.2 0 0055.1-55.2V303.1h7.7a47.2 47.2 0 0033.4-80.4zm-21.2 45.4a17 17 0 01-12.2 5h-22.7a15 15 0 00-15 15v168.7a25.2 25.2 0 01-25.1 25.2h-66.8V376.5a55.2 55.2 0 00-55.1-55.2h-48.2a55.2 55.2 0 00-55.2 55.2V482h-66.7a25.2 25.2 0 01-25.2-25.2V288.1a15 15 0 00-15-15h-23A17.2 17.2 0 0135.5 244L244.4 35a17 17 0 0124.2 0l208.8 208.8v.1a17.2 17.2 0 010 24.2zm0 0" /></svg>
        <svg viewBox="0 0 512 512" fill="currentColor">
          <path d="M467 76H45a45 45 0 00-45 45v270a45 45 0 0045 45h422a45 45 0 0045-45V121a45 45 0 00-45-45zm-6.3 30L287.8 278a44.7 44.7 0 01-63.6 0L51.3 106h409.4zM30 384.9V127l129.6 129L30 384.9zM51.3 406L181 277.2l22 22c14.2 14.1 33 22 53.1 22 20 0 38.9-7.9 53-22l22-22L460.8 406H51.3zM482 384.9L352.4 256 482 127V385z" /></svg>
        <svg viewBox="0 0 512 512" fill="currentColor">
          <path d="M272 512h-32c-26 0-47.2-21.1-47.2-47.1V454c-11-3.5-21.8-8-32.1-13.3l-7.7 7.7a47.1 47.1 0 01-66.7 0l-22.7-22.7a47.1 47.1 0 010-66.7l7.7-7.7c-5.3-10.3-9.8-21-13.3-32.1H47.1c-26 0-47.1-21.1-47.1-47.1v-32.2c0-26 21.1-47.1 47.1-47.1H58c3.5-11 8-21.8 13.3-32.1l-7.7-7.7a47.1 47.1 0 010-66.7l22.7-22.7a47.1 47.1 0 0166.7 0l7.7 7.7c10.3-5.3 21-9.8 32.1-13.3V47.1c0-26 21.1-47.1 47.1-47.1h32.2c26 0 47.1 21.1 47.1 47.1V58c11 3.5 21.8 8 32.1 13.3l7.7-7.7a47.1 47.1 0 0166.7 0l22.7 22.7a47.1 47.1 0 010 66.7l-7.7 7.7c5.3 10.3 9.8 21 13.3 32.1h10.9c26 0 47.1 21.1 47.1 47.1v32.2c0 26-21.1 47.1-47.1 47.1H454c-3.5 11-8 21.8-13.3 32.1l7.7 7.7a47.1 47.1 0 010 66.7l-22.7 22.7a47.1 47.1 0 01-66.7 0l-7.7-7.7c-10.3 5.3-21 9.8-32.1 13.3v10.9c0 26-21.1 47.1-47.1 47.1zM165.8 409.2a176.8 176.8 0 0045.8 19 15 15 0 0111.3 14.5V465c0 9.4 7.7 17.1 17.1 17.1h32.2c9.4 0 17.1-7.7 17.1-17.1v-22.2a15 15 0 0111.3-14.5c16-4.2 31.5-10.6 45.8-19a15 15 0 0118.2 2.3l15.7 15.7a17.1 17.1 0 0024.2 0l22.8-22.8a17.1 17.1 0 000-24.2l-15.7-15.7a15 15 0 01-2.3-18.2 176.8 176.8 0 0019-45.8 15 15 0 0114.5-11.3H465c9.4 0 17.1-7.7 17.1-17.1v-32.2c0-9.4-7.7-17.1-17.1-17.1h-22.2a15 15 0 01-14.5-11.2c-4.2-16.1-10.6-31.6-19-45.9a15 15 0 012.3-18.2l15.7-15.7a17.1 17.1 0 000-24.2l-22.8-22.8a17.1 17.1 0 00-24.2 0l-15.7 15.7a15 15 0 01-18.2 2.3 176.8 176.8 0 00-45.8-19 15 15 0 01-11.3-14.5V47c0-9.4-7.7-17.1-17.1-17.1h-32.2c-9.4 0-17.1 7.7-17.1 17.1v22.2a15 15 0 01-11.3 14.5c-16 4.2-31.5 10.6-45.8 19a15 15 0 01-18.2-2.3l-15.7-15.7a17.1 17.1 0 00-24.2 0l-22.8 22.8a17.1 17.1 0 000 24.2l15.7 15.7a15 15 0 012.3 18.2 176.8 176.8 0 00-19 45.8 15 15 0 01-14.5 11.3H47c-9.4 0-17.1 7.7-17.1 17.1v32.2c0 9.4 7.7 17.1 17.1 17.1h22.2a15 15 0 0114.5 11.3c4.2 16 10.6 31.5 19 45.8a15 15 0 01-2.3 18.2l-15.7 15.7a17.1 17.1 0 000 24.2l22.8 22.8a17.1 17.1 0 0024.2 0l15.7-15.7a15 15 0 0118.2-2.3z" />
          <path d="M256 367.4c-61.4 0-111.4-50-111.4-111.4s50-111.4 111.4-111.4 111.4 50 111.4 111.4-50 111.4-111.4 111.4zm0-192.8a81.5 81.5 0 000 162.8 81.5 81.5 0 000-162.8z" /></svg>
        <svg viewBox="0 0 512 512" fill="currentColor">
          <path d="M255.2 468.6H63.8a21.3 21.3 0 01-21.3-21.2V64.6c0-11.7 9.6-21.2 21.3-21.2h191.4a21.2 21.2 0 100-42.5H63.8A63.9 63.9 0 000 64.6v382.8A63.9 63.9 0 0063.8 511H255a21.2 21.2 0 100-42.5z" />
          <path d="M505.7 240.9L376.4 113.3a21.3 21.3 0 10-29.9 30.3l92.4 91.1H191.4a21.2 21.2 0 100 42.6h247.5l-92.4 91.1a21.3 21.3 0 1029.9 30.3l129.3-127.6a21.3 21.3 0 000-30.2z" /></svg>
      </div>
      <div class="main-container">
        <div class="header">
          <div class="logo">Rent
            <span class=logo-det>Cr</span></div>
          <a class="header-link active" href="#"><svg viewBox="-6 0 512 512" fill="currentColor">
            <path d="M227.7 357.5a15.1 15.1 0 0021.3 0l54-54a15.1 15.1 0 10-21.4-21.3l-43.3 43.2-19.7-19.7a15.1 15.1 0 00-21.4 21.4zm0 0" />
            <path d="M250.1 439.8a120.1 120.1 0 10-120-120c0 66.2 53.8 120 120 120zm0-209.7a89.9 89.9 0 010 179.5 89.9 89.9 0 010-179.5zm0 0" />
            <path d="M451.3 32.2h-27.5v-17a15.1 15.1 0 00-30.3 0v17h-29.7v-17a15.1 15.1 0 00-30.2 0v17h-167v-17a15.1 15.1 0 00-30.2 0v17h-29.7v-17a15.1 15.1 0 00-30.2 0v17H48.9A49 49 0 000 81v382A49 49 0 0048.9 512h402.4a49 49 0 0049-48.9v-382a49 49 0 00-49-49zm18.7 431c0 10.2-8.4 18.6-18.7 18.6H49A18.7 18.7 0 0130.2 463V158H470zM30.2 81c0-10.3 8.4-18.7 18.7-18.7h27.6v17.1a15.1 15.1 0 0030.2 0v-17h29.7v17a15.1 15.1 0 0030.3 0v-17h166.9v17a15.1 15.1 0 0030.2 0v-17h29.7v17a15.1 15.1 0 0030.3 0v-17h27.5c10.3 0 18.7 8.3 18.7 18.6v46.6H30.2zm0 0" />
          </svg>
            Bookings
          </a>
          <a class="header-link" href="#"><svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" fill="currentColor">
            <path d="M10 13a2 2 0 110-4 2 2 0 010 4zm0-2.5a.5.5 0 100 1 .5.5 0 000-1z" />
            <path d="M20.3 11.8h-8.8a.8.8 0 010-1.6h8.8a.8.8 0 010 1.6zM8.5 11.8H3.7a.8.8 0 010-1.6h4.8a.8.8 0 010 1.6zM15 19a2 2 0 110-4 2 2 0 010 4zm0-2.5a.5.5 0 100 1 .5.5 0 000-1z" />
            <path d="M20.3 17.8h-3.8a.8.8 0 010-1.6h3.8a.8.8 0 010 1.6zM13.5 17.8H3.7a.8.8 0 010-1.6h9.8a.8.8 0 010 1.6z" />
            <path d="M21.3 23H2.6A2.8 2.8 0 010 20.2V3.9C0 2.1 1.2 1 2.8 1h18.4C22.9 1 24 2.2 24 3.8v16.4c0 1.6-1.2 2.8-2.8 2.8zM2.6 2.5c-.6 0-1.2.6-1.2 1.3v16.4c0 .7.6 1.3 1.3 1.3h18.4c.7 0 1.3-.6 1.3-1.3V3.9c0-.7-.6-1.3-1.3-1.3z" />
            <path d="M23.3 6H.6a.8.8 0 010-1.5h22.6a.8.8 0 010 1.5z" />
          </svg>
            Controller
          </a>
          <a class="header-link" href="#"><svg viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg" fill="currentColor">
            <path d="M500 113.3C326.1 78.7 337.4 80.5 333.4 81.2L281 91.7A111.2 111.2 0 00176 17c-48.6 0-90 31.3-105 74.8L18 81.3A15 15 0 000 96v352a15 15 0 0012 14.7l162.2 32.2c3.6.4-7.6 2.3 161.8-31.6l158 31.4a15 15 0 0018-14.7V128a15 15 0 00-12-14.7zM176 47a81 81 0 0181 81c0 37.7-60.3 133.3-81 165-20.7-31.6-81-127.3-81-165a81 81 0 0181-81zM30 114.2l35.2 7a112 112 0 00-.2 6.8c0 25 16.4 65.4 50 123.4 19.7 33.9 39 63 46 73.2v137.1l-131-26zm161 210.4c7-10.2 26.3-39.3 46-73.2 33.6-58 50-98.4 50-123.4 0-2.3 0-4.6-.2-6.9l34.2-6.8v321.4l-130 26zm291 137.1l-131-26V114.3l131 26z" />
            <path d="M176 175a47 47 0 10-.1-94.1 47 47 0 00.1 94zm0-64a17 17 0 110 34 17 17 0 010-34z" /></svg>
            Map
          </a>
          <a class="header-link" href="#"><svg viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg" fill="currentColor">
            <path d="M76 240c12.1 0 23.1-4.8 31.2-12.6l44.2 22A44.9 44.9 0 00196 300a45 45 0 0040.6-64.4l60-60a45 45 0 0062.3-54l52.2-39.2a45 45 0 10-18-24l-52.2 39.2a45 45 0 00-65.5 56.8l-60 60a44.7 44.7 0 00-50.6 8.2l-44.2-22A44.9 44.9 0 0076 150a45 45 0 000 90zM436 30a15 15 0 110 30 15 15 0 010-30zm-120 90a15 15 0 110 30 15 15 0 010-30zM196 240a15 15 0 110 30 15 15 0 010-30zM76 180a15 15 0 110 30 15 15 0 010-30zm0 0" />
            <path d="M497 482h-16V165a15 15 0 00-15-15h-60a15 15 0 00-15 15v317h-30V255a15 15 0 00-15-15h-60a15 15 0 00-15 15v227h-30V375a15 15 0 00-15-15h-60a15 15 0 00-15 15v107h-30V315a15 15 0 00-15-15H46a15 15 0 00-15 15v167H15a15 15 0 100 30h482a15 15 0 100-30zm-76-302h30v302h-30zm-120 90h30v212h-30zM181 390h30v92h-30zM61 330h30v152H61zm0 0" />
          </svg>
            Reports
          </a>
          <div class="user-info">
            <button class="button">All
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-chevron-down">
                <path d="M6 9l6 6 6-6" /></svg>
            </button>
            <div class="user-name">Mike J</div>
            <svg class="profile" viewBox="-42 0 512 512" fill="currentColor">
              <path d="M210.4 246.6c33.8 0 63.2-12.1 87.1-36.1 24-24 36.2-53.3 36.2-87.2 0-33.9-12.2-63.2-36.2-87.2-24-24-53.3-36.1-87.1-36.1-34 0-63.3 12.2-87.2 36.1S87 89.4 87 123.3c0 33.9 12.2 63.2 36.2 87.2 24 24 53.3 36.1 87.2 36.1zm-66-189.3a89.1 89.1 0 0166-27.3c26 0 47.5 9 66 27.3a89.2 89.2 0 0127.3 66c0 26-9 47.6-27.4 66a89.1 89.1 0 01-66 27.3c-26 0-47.5-9-66-27.3a89.1 89.1 0 01-27.3-66c0-26 9-47.6 27.4-66zm0 0M426.1 393.7a304.6 304.6 0 00-12-64.9 160.7 160.7 0 00-13.5-30.3c-5.7-10.2-12.5-19-20.1-26.3a88.9 88.9 0 00-29-18.2 100.1 100.1 0 00-37-6.7c-5.2 0-10.2 2.2-20 8.5-6 4-13 8.5-20.9 13.5-6.7 4.3-15.8 8.3-27 11.9a107.3 107.3 0 01-66 0 119.3 119.3 0 01-27-12l-21-13.4c-9.7-6.3-14.8-8.5-20-8.5a100 100 0 00-37 6.7 88.8 88.8 0 00-29 18.2 114.4 114.4 0 00-20.1 26.3 161 161 0 00-13.4 30.3A302.5 302.5 0 001 393.7c-.7 9.8-1 20-1 30.2 0 26.8 8.5 48.4 25.3 64.4C41.8 504 63.6 512 90.3 512h246.5c26.7 0 48.6-8 65.1-23.7 16.8-16 25.3-37.6 25.3-64.4a437 437 0 00-1-30.2zm-44.9 72.8c-11 10.4-25.4 15.5-44.4 15.5H90.3c-19 0-33.4-5-44.4-15.5C35.2 456.3 30 442.4 30 424c0-9.5.3-19 1-28.1A272.9 272.9 0 0141.7 338a131 131 0 0110.9-24.7A84.8 84.8 0 0167.4 294a59 59 0 0119.3-12 69 69 0 0123.6-4.5c1 .5 3 1.6 6 3.6l21 13.6c9 5.6 20.4 10.7 34 15.1a137.3 137.3 0 0084.5 0c13.7-4.4 25.1-9.5 34-15.1a2721 2721 0 0027-17.2 69 69 0 0123.7 4.5 59 59 0 0119.2 12 84.5 84.5 0 0114.9 19.4c4.5 8 8.2 16.3 10.8 24.7a275.2 275.2 0 0110.8 57.8c.6 9 1 18.5 1 28.1 0 18.5-5.3 32.4-16 42.6zm0 0" /></svg>
            <div class="hour">08.20 pm</div>
          </div>
        </div>
        <div class="user-box first-box">
          <div class="account-wrapper" style="--delay: .8s">
            <div class="account-profile">
              <img src="https://images.unsplash.com/photo-1550314124-301ca0b773ae?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=2215&q=80" alt="">
              <div class="blob-wrap">
                <div class="blob"></div>
                <div class="blob"></div>
                <div class="blob"></div>
              </div>
              <div class="account-name">Mike J Morgan</div>
              <div class="account-title">Taxi Driver</div>
            </div>
            <div class="account card">
              <div class="account-cash">$ 5637.04</div>
              <div class="account-income">Total Income</div>
              <div class="account-iban">**** **** **** 3060</div>
            </div>
          </div>
          <div class="activity card" style="--delay: .2s">
            <div class="title">User Activities</div>
            <div class="subtitle">Hoo - is an adaptive Online Courses Application with a wide range of course directions. The students will have a great possibility to study.</div>
            <div class="activity-links">
              <div class="activity-link active">Current User</div>
              <div class="activity-link notify">User Request</div>
            </div>
            <div class="destination">
              <div class="destination-card">
                <div class="destination-profile">
                  <img class="profile-img" src="https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1650&q=80" alt="" />
                  <div class="destination-length">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-map-pin">
                      <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0118 0z" />
                      <circle cx="12" cy="10" r="3" />
                    </svg>
                    45.4m
                  </div>
                </div>
                <div class="destination-points">
                  <div class="point">Traffic Point</div>
                  <div class="sub-point">Brooklyn St, NY</div>
                </div>
              </div>
              <div class="destination-card">
                <div class="destination-profile">
                  <img class="profile-img" src="https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1400&q=80" alt="" />
                  <div class="destination-length">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-map-pin">
                      <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0118 0z" />
                      <circle cx="12" cy="10" r="3" />
                    </svg>
                    42.8m
                  </div>
                </div>
                <div class="destination-points">
                  <div class="point">Pickup Point</div>
                  <div class="sub-point">Maryland 17, NY</div>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500&display=swap");
:root {
  --delay: 0s;
}

* {
  outline: none;
  box-sizing: border-box;
}

html {
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
}

img {
  max-width: 100%;
}

body {
  font-family: "Inter", sans-serif;
  background-color: #252954;
  color: #9b9ca7;
}

.wrapper {
  max-width: 1600px;
  background-color: #0e0e23;
  display: flex;
  height: 100vh;
  overflow: hidden;
  margin: 0 auto;
}

.left-side {
  width: 6rem;
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  align-items: center;
  padding: 117px 0 40px;
  animation: left 1s var(--delay) both;
}
.left-side svg {
  width: 24px;
  cursor: pointer;
}
.left-side svg + svg {
  margin-top: 34px;
}
.left-side svg:last-child {
  margin-top: auto;
}
.left-side svg.active, .left-side svg:hover {
  color: #4255d4;
}

@keyframes left {
  0% {
    transform: translateX(-30px);
    opacity: 0;
  }
  100% {
    opacity: 1;
    transform: none;
  }
}
@keyframes top {
  0% {
    transform: translateY(-30px);
    opacity: 0;
  }
  100% {
    opacity: 1;
    transform: none;
  }
}
.main-container {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  padding: 25px;
  overflow: auto;
  padding-left: 0;
}

.header {
  background: #141834;
  background: radial-gradient(circle, #141834 0%, #13162f 100%);
  box-shadow: 0 16px 12px #0e0e23;
  width: 100%;
  padding: 0 30px;
  animation: top 1s both;
  display: flex;
  align-items: center;
  border-radius: 6px;
  font-size: 15px;
  white-space: nowrap;
  position: sticky;
  top: 0;
  left: 0;
  z-index: 10;
}
.header:before {
  content: "";
  width: 100%;
  height: 25px;
  position: absolute;
  top: -25px;
  left: 0;
  background-color: #0e0e23;
}
.header-link {
  color: #9b9ca7;
  text-decoration: none;
  display: flex;
  align-items: center;
  padding: 20px;
  transition: 0.3s;
  border-bottom: 3px solid transparent;
  transition: 0.3s;
}
.header-link svg {
  width: 20px;
  margin-right: 14px;
}
.header-link.active, .header-link:hover {
  background: #11132c;
  border-bottom: 3px solid #4255d4;
}

.logo {
  padding: 20px 50px 20px 0;
  font-size: 16px;
  color: #e7e8ea;
}
.logo-det {
  background: #4255d4;
  padding: 8px;
  margin-left: -2px;
  border-radius: 50%;
  font-size: 15px;
}

.user-info {
  margin-left: auto;
  display: flex;
  align-items: center;
}
.user-info svg {
  width: 20px;
}
.user-info .profile {
  margin: 0 20px 0 12px;
  width: 18px;
}

.button {
  display: flex;
  align-items: center;
  color: #9b9ca7;
  background: #1a1b3c;
  border: none;
  padding: 2px 12px;
  border-radius: 4px;
  margin-right: 20px;
}
.button svg {
  margin-left: 10px;
  width: 16px;
}

.user-box {
  margin-top: 25px;
  display: flex;
}
.user-box + .user-box {
  margin-top: 20px;
}

.user-box .cards-header svg,
.user-box .cards-view svg {
  width: 24px;
}
.user-box .cards-header .title,
.user-box .cards-view .title {
  margin: 0 16px;
  font-size: 15px;
}
.user-box .cards-header-date svg,
.user-box .cards-view-date svg {
  width: 30px;
}
.user-box .cards-header .cards-button,
.user-box .cards-view .cards-button {
  background: #15193c;
  padding: 10px 16px;
  margin-right: 0;
}
.user-box .cards-header .cards-button svg,
.user-box .cards-view .cards-button svg {
  width: 16px;
  margin: 0 2px 0 0;
}
.user-box .cards-header .date-wrapper,
.user-box .cards-view .date-wrapper {
  display: flex;
  align-items: center;
  margin: auto;
}
.user-box .cards-header .date-wrapper .title,
.user-box .cards-view .date-wrapper .title {
  margin: 0 16px;
}

.cards-view > svg {
  margin-right: 12px;
}



.card {
  background: #1a2049;
  background: radial-gradient(circle, #1a2049 0%, #13162f 100%);
  padding: 40px 30px;
  border-radius: 6px;
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  max-height: 430px;
  width: 100%;
}
.card .title {
  font-size: 16px;
  font-weight: 500;
}
.card .subtitle {
  font-size: 13px;
  line-height: 1.6em;
}
.card + .card {
  margin-left: 20px;
}

.activity {
  max-width: 480px;
}
.activity .title {
  margin-bottom: 20px;
}
.activity-links {
  display: flex;
  align-items: center;
  margin-top: auto;
  font-size: 15px;
}
.activity-link {
  padding-bottom: 10px;
  position: relative;
  cursor: pointer;
  transition: 0.3s;
}
.activity-link + .activity-link {
  margin-left: 25px;
}
.activity-link + .activity-link:before {
  content: "";
  position: absolute;
  width: 5px;
  height: 5px;
  background-color: #ef415c;
  top: -2px;
  right: -8px;
  border-radius: 50%;
}
.activity-link + .activity-link:hover:after {
  content: "";
  position: absolute;
  width: 22px;
  height: 2px;
  background: #4255d4;
  left: 0;
  bottom: 0;
}
.activity-link + .activity-link:hover {
  color: #bebec4;
  -webkit-text-stroke: 0.3px;
}
.activity-link.active {
  color: #bebec4;
  font-weight: 500;
}
.activity-link.active:before {
  content: "";
  position: absolute;
  width: 22px;
  height: 2px;
  background: #4255d4;
  left: 0;
  bottom: 0;
}

.destination {
  display: flex;
  align-items: center;
  margin-top: auto;
}
.destination-card {
  background: #1a1d3a;
  background: linear-gradient(45deg, #1a1d3a 0%, #212752 100%);
  padding: 20px;
  width: 100%;
  border-radius: 6px;
}
.destination-card + .destination-card {
  margin-left: 20px;
  background: #1a1d3a;
  background: linear-gradient(325deg, #1a1d3a 0%, #212752 100%);
}
.destination-profile {
  display: flex;
  align-items: center;
  font-size: 14px;
}
.destination-profile svg {
  width: 18px;
  flex-shrink: 0;
  margin-right: 8px;
}
.destination-length {
  margin-left: auto;
  display: flex;
  align-items: center;
  font-size: 13px;
}
.destination-points {
  margin-top: 30px;
}

.profile-img {
  width: 46px;
  height: 46px;
  object-fit: cover;
  border-radius: 50%;
  padding: 2px;
  border: 1px solid #bebec4;
  flex-shrink: 0;
}

.point {
  font-size: 14px;
  color: #bebec4;
  font-weight: 500;
}

.sub-point {
  font-size: 13px;
  margin-top: 4px;
}



@keyframes pieChart {
  100% {
    stroke-dashoffset: 0;
  }
}

.cards-header-date svg {
  color: #4154d1;
  background: #14183c;
  border-radius: 50%;
  padding: 5px;
  width: 30px;
}
.degree svg {
  width: 24px;
  margin-right: 12px;
}

.

.account {
  width: 100%;
  height: 180px;
  margin-top: auto;
  flex-grow: 0;
  position: relative;
  transition: 0.3s;
  cursor: pointer;
  transition: 0.3s;
}
.account:hover {
  transform: scale(1.02);
}
.account:before {
  content: "";
  position: absolute;
  width: 24px;
  height: 24px;
  box-shadow: -15px 0 0 0 #ef8741;
  background: #ef415c;
  top: 20px;
  left: 42px;
  border-radius: 50%;
}
.account-wrapper {
  max-width: 310px;
  width: 100%;
  display: flex;
  flex-direction: column;
  margin-left: 20px;
  align-items: center;
}
.account-profile {
  margin: auto;
  position: relative;
  text-align: center;
  position: relative;
}
.account-profile img {
  width: 84px;
  height: 84px;
  border-radius: 50%;
  object-fit: cover;
  object-position: left;
  border: 3px solid #4255d3;
  padding: 5px;
}
.account-profile .blob {
  position: absolute;
  border-radius: 50%;
  animation: fly 5.8s linear infinite alternate;
}
.account-profile .blob:nth-child(1) {
  width: 14px;
  height: 14px;
  top: 25px;
  left: -20px;
  background: #28327a;
  animation-delay: 0.9s;
}
.account-profile .blob:nth-child(2) {
  width: 18px;
  height: 18px;
  background: #87344c;
  right: -20px;
  top: -20px;
  animation-delay: 0.2s;
}
.account-profile .blob:nth-child(3) {
  width: 12px;
  height: 12px;
  background: #13645b;
  right: -35px;
  top: 50%;
  animation-delay: 1.8s;
}
.account-name {
  margin: 20px 0 10px;
}
.account-title {
  font-size: 14px;
}
.account-cash {
  font-size: 22px;
  font-weight: 500;
  margin-bottom: 6px;
  padding-top: 16px;
  position: relative;
}
.account-cash:before {
  content: "";
  position: absolute;
  width: 5px;
  height: 5px;
  background: #9b9ca7;
  right: 10px;
  border-radius: 50%;
  box-shadow: -10px 0 0 0 #9b9ca7, 10px 0 0 0 #9b9ca7;
  top: 24px;
}
.account-income {
  font-size: 14px;
}
.account-iban {
  margin-top: auto;
  font-weight: 500;
}

.table .status svg {
  margin-right: 6px;
  width: 22px;
  height: 22px;
  padding: 3px;
  border-radius: 4px;
  background-color: #142940;
  color: currentColor;
}

.table .status.is-red svg {
  background: #2e2142;
  color: currentcolor;
}

.table .status.is-wait svg {
  background-color: transparent;
  color: currentcolor;
  animation: turn 2s linear infinite both;
}

@keyframes turn {
  100% {
    transform: rotate(1turn);
  }
}

.table th {
  font-size: 14px;
  font-weight: normal;
  padding-bottom: 16px;
}
.table th:nth-child(n+5) {
  padding: 0 10px 16px;
}
.table th:first-child {
  padding-left: 36px;
}
.table td {
  font-size: 15px;
  vertical-align: middle;
  padding: 8px 0;
}
.table td:last-of-type {
  width: 100px;
}
.table td:nth-child(n+5) {
  padding: 0 10px;
}
.table td:nth-last-of-type(2) svg {
  width: 16px;
}
.table input {
  appearance: none;
  width: 16px;
  height: 16px;
  border: 1px solid #9b9ca7;
  vertical-align: middle;
  background-color: transparent;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 16px;
  transition: 0.25s;
  background-size: 0;
  background-position: center;
}
.table input:checked {
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' width='24' height='24' stroke='%23fff' stroke-width='2' fill='none' stroke-linecap='round' stroke-linejoin='round' class='css-i6dzq1'%3E%3Cpolyline points='20 6 9 17 4 12'%3E%3C/polyline%3E%3C/svg%3E");
  background-color: #252954;
  background-size: 12px;
  background-repeat: no-repeat;
}

@keyframes fly {
  40% {
    transform: translate(-6px, -6px);
  }
  60% {
    transform: translate(-12px, -2px);
  }
  100% {
    transform: translate(0px, 0px);
  }
}

.credit-wrapper svg {
  width: 38px;
  padding: 3px;
  border-radius: 6px;
  margin-right: 12px;
}
.credit-wrapper path {
  width: 24px;
}


@media screen and (max-width: 1500px) {
  .wrapper {
    max-width: 1200px;
  }

  .activity {
    width: 49%;
    max-width: none;
  }

  .first-box {
    flex-wrap: wrap;
  }
}

@media screen and (max-width: 1060px) {
  .user-info .button,
  .user-info .hour {
    display: none;
  }
}

@media screen and (max-width: 930px) {

  .header-link {
    display: none;
  }

  .user-info .profile {
    margin-right: 0;
  }
}
@media screen and (max-width: 850px) {

  .user-box .discount {
    margin-left: 0;
    margin-top: 20px;
  }

  .left-side {
    display: none;
  }

  .main-container {
    padding-left: 25px;
  }

  .activity-links,
  .destination {
    margin-top: 20px;
  }
}
@media screen and (max-width: 720px) {
  .first-box .account-wrapper {
    display: none;
  }
}
@media screen and (max-width: 420px) {
  .destination-card + .destination-card {
    display: none;
  }
}
.user-box > * {
  animation: top 1s var(--delay) both;
}

</style>