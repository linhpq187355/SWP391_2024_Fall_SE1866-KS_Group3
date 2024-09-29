<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: white;
            color: #4a5568; /* text-gray-700 */
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 4rem 1rem;
        }
        .text-center {
            text-align: center;
        }
        .mb-12 {
            margin-bottom: 3rem;
        }
        .mb-4 {
            margin-bottom: 1rem;
        }
        .font-bold {
            font-weight: bold;
        }
        .text-6xl {
            font-size: 4rem; /* text-6xl */
        }
        .text-xl {
            font-size: 1.25rem; /* text-xl */
        }
        .text-yellow-500 {
            color: #d69e2e; /* yellow-500 */
        }
        .bg-yellow-100 {
            background-color: #fefcbf; /* yellow-100 */
        }
        .rounded-lg {
            border-radius: 0.5rem; /* rounded-lg */
        }
        .p-4 {
            padding: 1rem; /* p-4 */
        }
        .mr-4 {
            margin-right: 1rem; /* mr-4 */
        }
        .flex {
            display: flex;
        }
        .items-start {
            align-items: flex-start;
        }
        .grid {
            display: grid;
            gap: 2rem; /* gap-8 */
        }
        @media (min-width: 768px) {
            .grid-cols-1 {
                grid-template-columns: repeat(1, 1fr);
            }
            .md\:grid-cols-2 {
                grid-template-columns: repeat(2, 1fr);
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="text-center mb-12">
        <h1 class="text-6xl font-bold text-yellow-500">About</h1>
    </div>
    <div class="max-w-2xl mx-auto mb-12">
        <p class="mb-4">Oh feel if up to till like. He an thing rapid these after going drawn or. Timed she his law the spoil round defer. In surprise concerns informed betrayed he learning is ye. Ignorant formerly so ye blessing.</p>
        <p class="font-bold">He as spoke avoid given downs money on we. Of properly carriage shutters ye as wandered up repeated moreover.</p>
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2">
        <div class="flex items-start">
            <div class="bg-yellow-100 p-4 rounded-lg mr-4">
                <i class="fas fa-mobile-alt text-3xl text-yellow-500"></i>
            </div>
            <div>
                <h2 class="text-xl font-bold text-yellow-500 mb-2">Research</h2>
                <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
            </div>
        </div>
        <div class="flex items-start">
            <div class="bg-yellow-100 p-4 rounded-lg mr-4">
                <i class="fas fa-cogs text-3xl text-yellow-500"></i>
            </div>
            <div>
                <h2 class="text-xl font-bold text-yellow-500 mb-2">Strategy</h2>
                <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
            </div>
        </div>
        <div class="flex items-start">
            <div class="bg-yellow-100 p-4 rounded-lg mr-4">
                <i class="fas fa-bell text-3xl text-yellow-500"></i>
            </div>
            <div>
                <h2 class="text-xl font-bold text-yellow-500 mb-2">Design</h2>
                <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
            </div>
        </div>
        <div class="flex items-start">
            <div class="bg-yellow-100 p-4 rounded-lg mr-4">
                <i class="fas fa-lightbulb text-3xl text-yellow-500"></i>
            </div>
            <div>
                <h2 class="text-xl font-bold text-yellow-500 mb-2">Referring</h2>
                <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
