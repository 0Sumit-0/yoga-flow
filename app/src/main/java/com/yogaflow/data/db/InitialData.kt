package com.yogaflow.data.db

import com.yogaflow.data.model.FavoritePose
import com.yogaflow.data.model.PlanPoseItem
import com.yogaflow.data.model.PracticeCompletion
import com.yogaflow.data.model.TargetPlan
import com.yogaflow.data.model.UserProfile
import com.yogaflow.data.model.YogaPose
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object InitialData {

    val defaultProfile = UserProfile(
        id = "user_default",
        email = "practitioner@yogaflow.app",
        name = "Elena",
        age = 29,
        gender = "Female",
        level = "Beginner",
        goals = listOf("Stress Relief", "Better Sleep", "Flexibility"),
        createdAt = System.currentTimeMillis()
    )

    val poses = listOf(
        YogaPose(
            slug = "childs-pose",
            name = "Child's Pose",
            sanskritName = "Balasana",
            category = "Restorative",
            targets = listOf("Stress Relief", "Sleep", "Digestion"),
            difficulty = "Beginner",
            durationSeconds = 90,
            instructions = listOf(
                "Kneel on the mat with big toes touching and knees wide apart.",
                "Exhale and fold torso forward between your thighs.",
                "Extend your arms long in front of you with palms flat, or rest arms back along your sides.",
                "Rest your forehead gently on the mat and soften your shoulders.",
                "Breathe deeply into your lower back and belly."
            ),
            benefits = listOf(
                "Calms the nervous system and relieves fatigue",
                "Gently stretches hips, thighs, and ankles",
                "Compresses abdominal organs to support digestion",
                "Releases tension along the spine and neck"
            ),
            breathingCue = "Slow, expansive inhales into the back ribs; soft exhales releasing into the earth.",
            modifications = "Place a bolster or folded blanket under your torso or hips if knees or hips feel tight.",
            iconType = "restorative"
        ),
        YogaPose(
            slug = "cat-cow-stretch",
            name = "Cat-Cow Flow",
            sanskritName = "Marjaryasana-Bitilasana",
            category = "Seated",
            targets = listOf("Digestion", "Flexibility", "Energy", "Stress Relief"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Begin on hands and knees with wrists under shoulders and knees under hips.",
                "Inhale for Cow: Drop your belly toward the mat, lift your chest and gaze upward.",
                "Exhale for Cat: Draw your belly button to your spine, round your back, and tuck your chin.",
                "Continue undulating smoothly with your breath for 5 to 8 rounds."
            ),
            benefits = listOf(
                "Massages internal digestive organs and stimulates gut motility",
                "Increases spinal mobility and awakens circulation",
                "Releases tension in the neck, chest, and lower back"
            ),
            breathingCue = "Inhale open the heart into Cow; exhale round the spine completely into Cat.",
            modifications = "Place a folded blanket beneath your knees for extra cushioning.",
            iconType = "seated"
        ),
        YogaPose(
            slug = "downward-facing-dog",
            name = "Downward-Facing Dog",
            sanskritName = "Adho Mukha Svanasana",
            category = "Inversion",
            targets = listOf("Energy", "Flexibility", "Mental Focus"),
            difficulty = "Beginner",
            durationSeconds = 75,
            instructions = listOf(
                "From hands and knees, tuck your toes and lift your hips high toward the ceiling.",
                "Spread fingers wide, press evenly through knuckles, and lengthen through your armpits.",
                "Pedal out your heels gently to warm up hamstrings and calves.",
                "Draw your shoulder blades down your back and relax your neck between upper arms."
            ),
            benefits = listOf(
                "Invigorates full body circulation and clears mental fog",
                "Deeply stretches hamstrings, calves, and shoulders",
                "Strengthens arms, legs, and stabilizing core muscles"
            ),
            breathingCue = "Inhale lengthen from palms up to tailbone; exhale press heels softly down toward the ground.",
            modifications = "Keep a generous bend in your knees to keep spine long and tall.",
            iconType = "inversion"
        ),
        YogaPose(
            slug = "warrior-two",
            name = "Warrior II",
            sanskritName = "Virabhadrasana II",
            category = "Standing",
            targets = listOf("Mental Focus", "Energy", "Flexibility"),
            difficulty = "Intermediate",
            durationSeconds = 60,
            instructions = listOf(
                "Step feet wide (3-4 feet apart). Turn right toes forward and left foot slightly in.",
                "Extend arms parallel to floor, active through fingertips at shoulder height.",
                "Bend front right knee directly over your right ankle at 90 degrees.",
                "Fix your steady gaze (drishti) over the middle finger of the front hand."
            ),
            benefits = listOf(
                "Cultivates unwavering mental focus and grounded determination",
                "Strengthens legs, ankles, and arches",
                "Opens hips and chest while building stamina"
            ),
            breathingCue = "Inhale draw energy up from feet; exhale sink steadily into the front knee.",
            modifications = "Shorten your stance if inner thighs or hips feel strained.",
            iconType = "standing"
        ),
        YogaPose(
            slug = "tree-pose",
            name = "Tree Pose",
            sanskritName = "Vrikshasana",
            category = "Balancing",
            targets = listOf("Mental Focus", "Flexibility", "Stress Relief"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Stand tall in Mountain pose with weight evenly distributed.",
                "Shift weight into left foot. Place the sole of right foot against inner left calf or thigh (avoid knee).",
                "Bring hands together in prayer (Anjali Mudra) at heart center or reach overhead.",
                "Fix gaze on an unmoving point in front of you and breathe steadily."
            ),
            benefits = listOf(
                "Enhances single-pointed concentration and equilibrium",
                "Strengthens ankles, calves, and core stabilizing muscles",
                "Gently opens inner groin and outer hip"
            ),
            breathingCue = "Inhale root down through standing sole; exhale grow tall through crown of head.",
            modifications = "Keep right toes lightly touching the mat as a kickstand for balance.",
            iconType = "balance"
        ),
        YogaPose(
            slug = "seated-forward-bend",
            name = "Seated Forward Bend",
            sanskritName = "Paschimottanasana",
            category = "Forward Bend",
            targets = listOf("Digestion", "Sleep", "Flexibility", "Stress Relief"),
            difficulty = "Beginner",
            durationSeconds = 90,
            instructions = listOf(
                "Sit tall with legs straight in front of you, feet flexed.",
                "Inhale, reach arms high above head and lengthen spine from sit bones.",
                "Exhale, hinge at your hips and fold torso forward over your thighs.",
                "Hold shins, ankles, or outer feet without pulling or rounding excessively."
            ),
            benefits = listOf(
                "Soothes adrenal glands and triggers parasympathetic relaxation",
                "Deeply stretches hamstrings, glutes, and lower back",
                "Tones abdominal organs to aid digestion"
            ),
            breathingCue = "Inhale lengthen spine forward; exhale melt chest closer toward thighs.",
            modifications = "Bend knees slightly or use a yoga strap looped around the balls of feet.",
            iconType = "forward_bend"
        ),
        YogaPose(
            slug = "cobra-pose",
            name = "Cobra Pose",
            sanskritName = "Bhujangasana",
            category = "Backbend",
            targets = listOf("Energy", "Digestion", "Flexibility"),
            difficulty = "Beginner",
            durationSeconds = 45,
            instructions = listOf(
                "Lie prone on your belly with legs extended and tops of feet on the mat.",
                "Place palms on the mat under shoulders, hugging elbows close to ribs.",
                "Inhale, press tops of feet down and gently peel chest off the mat.",
                "Keep shoulders relaxed away from ears with a gentle lift through the heart."
            ),
            benefits = listOf(
                "Stimulates digestion by increasing intra-abdominal pressure",
                "Elevates mood and combats lethargy by expanding chest",
                "Strengthens the posterior chain and spinal erectors"
            ),
            breathingCue = "Inhale lift chest forward and up; exhale broaden across collarbones.",
            modifications = "Stay in Baby Cobra with minimal weight on hands to protect lower back.",
            iconType = "backbend"
        ),
        YogaPose(
            slug = "supine-spinal-twist",
            name = "Supine Spinal Twist",
            sanskritName = "Supta Matsyendrasana",
            category = "Twist",
            targets = listOf("Digestion", "Stress Relief", "Sleep"),
            difficulty = "Beginner",
            durationSeconds = 75,
            instructions = listOf(
                "Lie flat on your back and hug both knees into your chest.",
                "Extend arms out into a T-shape at shoulder level with palms up.",
                "Exhale and let both knees drop slowly to the right side.",
                "Turn head gently to gaze over left shoulder if comfortable on neck."
            ),
            benefits = listOf(
                "Wrings out digestive tract and relieves gas and bloating",
                "Releases tightness in lower back, hips, and glutes",
                "Calms the sympathetic nervous system before sleep"
            ),
            breathingCue = "Inhale fill the belly; exhale let the shoulders melt completely into the earth.",
            modifications = "Place a pillow or block under knees if they don't rest comfortably on floor.",
            iconType = "twist"
        ),
        YogaPose(
            slug = "pigeon-pose",
            name = "Half Pigeon Pose",
            sanskritName = "Eka Pada Rajakapotasana",
            category = "Restorative",
            targets = listOf("Stress Relief", "Flexibility", "Sleep"),
            difficulty = "Intermediate",
            durationSeconds = 90,
            instructions = listOf(
                "From Downward Dog, bring right knee forward behind right wrist.",
                "Angle right foot toward left hip, and slide left leg straight back with top of foot down.",
                "Square hips toward the front of the mat.",
                "Exhale and slowly walk hands forward, lowering torso onto forearms or mat."
            ),
            benefits = listOf(
                "Releases deeply stored emotional stress and tension in hip rotators",
                "Stretches glutes, piriformis, and psoas muscles",
                "Promotes deep introspective stillness and breath connection"
            ),
            breathingCue = "Direct long, soothing exhales into the right outer hip and pelvic bowl.",
            modifications = "Take Figure 4 (Reclined Pigeon) on your back if knee pressure arises.",
            iconType = "restorative"
        ),
        YogaPose(
            slug = "legs-up-the-wall",
            name = "Legs-Up-The-Wall",
            sanskritName = "Viparita Karani",
            category = "Inversion",
            targets = listOf("Sleep", "Stress Relief", "Digestion"),
            difficulty = "Beginner",
            durationSeconds = 120,
            instructions = listOf(
                "Sit sideways next to a clear wall with hips close to the baseboard.",
                "Gently swing legs up the wall as you recline your back onto the mat.",
                "Rest arms comfortably alongside body or on your belly.",
                "Close eyes and surrender all muscular effort in legs and feet."
            ),
            benefits = listOf(
                "Ultimate nervous system reset for restful sleep",
                "Enhances lymphatic drainage and relieves heavy, swollen legs",
                "Lowers heart rate and eases chronic stress"
            ),
            breathingCue = "Allow the breath to become effortless, slow, and natural like a calm tide.",
            modifications = "Slide a bolster or folded blanket under sacrum for gentle elevation.",
            iconType = "inversion"
        ),
        YogaPose(
            slug = "bridge-pose",
            name = "Bridge Pose",
            sanskritName = "Setu Bandhasana",
            category = "Backbend",
            targets = listOf("Energy", "Mental Focus", "Digestion"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Lie on your back with knees bent, feet hip-width apart and flat on the floor.",
                "Place arms alongside body with fingertips grazing heels.",
                "Press firmly through feet and arms to lift hips and chest toward ceiling.",
                "Clasp hands under back or keep palms flat, rolling shoulders underneath."
            ),
            benefits = listOf(
                "Revitalizes body and stimulates thyroid & digestive organs",
                "Strengthens glutes, hamstrings, and lower back",
                "Opens chest, shoulders, and heart center"
            ),
            breathingCue = "Inhale expand chest toward chin; exhale ground firmly through all four corners of feet.",
            modifications = "Place a yoga block beneath your sacrum for a restorative supported bridge.",
            iconType = "backbend"
        ),
        YogaPose(
            slug = "triangle-pose",
            name = "Triangle Pose",
            sanskritName = "Trikonasana",
            category = "Standing",
            targets = listOf("Flexibility", "Mental Focus", "Digestion"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Stand with feet 3-4 feet apart. Turn right foot out 90 degrees and left foot in 15 degrees.",
                "Inhale arms wide. Exhale reach right arm far forward over right leg.",
                "Hinge at right hip and bring right hand down to shin, ankle, or block.",
                "Extend left arm straight up toward sky and turn gaze gently upward."
            ),
            benefits = listOf(
                "Opens hamstrings, groins, and lateral body",
                "Massages abdominal organs to aid digestive motility",
                "Sharpens mental clarity through geometric alignment"
            ),
            breathingCue = "Inhale lengthen both sides of torso; exhale rotate ribs open toward sky.",
            modifications = "Rest lower hand on a yoga block placed outside the front calf.",
            iconType = "standing"
        ),
        YogaPose(
            slug = "happy-baby-pose",
            name = "Happy Baby Pose",
            sanskritName = "Ananda Balasana",
            category = "Restorative",
            targets = listOf("Sleep", "Stress Relief", "Digestion"),
            difficulty = "Beginner",
            durationSeconds = 75,
            instructions = listOf(
                "Lie flat on your back and draw knees toward armpits.",
                "Flex feet so soles face ceiling with shins perpendicular to floor.",
                "Grip outer edges of feet with hands and gently pull knees downward.",
                "Gently rock side to side to massage the lower spine and sacrum."
            ),
            benefits = listOf(
                "Deeply relaxes pelvic floor, inner groins, and hips",
                "Gently decompresses the lumbar spine",
                "Induces playful ease and releases nocturnal restlessness"
            ),
            breathingCue = "Breathe down into the belly, exhaling any tension from the jaw and hips.",
            modifications = "Hold back of thighs or calves if holding feet causes neck to arch.",
            iconType = "restorative"
        ),
        YogaPose(
            slug = "corpse-pose",
            name = "Corpse Pose",
            sanskritName = "Savasana",
            category = "Restorative",
            targets = listOf("Sleep", "Stress Relief", "Mental Focus"),
            difficulty = "Beginner",
            durationSeconds = 120,
            instructions = listOf(
                "Lie completely flat on your back, letting feet flop open naturally.",
                "Rest arms alongside body, slightly away from torso with palms facing up.",
                "Close eyes, soften forehead, unclench jaw, and release tongue from roof of mouth.",
                "Allow total stillness and surrender all control of body and breath."
            ),
            benefits = listOf(
                "Integrates the energetic benefits of the entire practice",
                "Reduces blood pressure, anxiety, and insomnia",
                "Cultivates profound inner peace and mindful presence"
            ),
            breathingCue = "No effort. Simply observe the gentle rise and fall of your natural breath.",
            modifications = "Place a bolster or rolled blanket under knees to relieve lower back pressure.",
            iconType = "restorative"
        ),
        YogaPose(
            slug = "camel-pose",
            name = "Camel Pose",
            sanskritName = "Ustrasana",
            category = "Backbend",
            targets = listOf("Energy", "Flexibility", "Digestion"),
            difficulty = "Intermediate",
            durationSeconds = 45,
            instructions = listOf(
                "Kneel upright on mat with knees hip-width apart and thighs perpendicular to floor.",
                "Place hands on lower back with fingers pointing down, supporting sacrum.",
                "Inhale lift chest toward ceiling, draw elbows together, and lean back into upper spine.",
                "If comfortable, reach hands back to hold heels while pressing hips forward."
            ),
            benefits = listOf(
                "Powerful energy booster and fatigue reducer",
                "Stretches entire front body, hip flexors, chest, and throat",
                "Stimulates digestion and opens respiratory capacity"
            ),
            breathingCue = "Inhale lift heart skyward; exhale ground firmly through shins and tops of feet.",
            modifications = "Keep hands supporting lower back and tuck toes to elevate heels.",
            iconType = "backbend"
        ),
        YogaPose(
            slug = "puppy-pose",
            name = "Extended Puppy Pose",
            sanskritName = "Uttana Shishosana",
            category = "Restorative",
            targets = listOf("Stress Relief", "Sleep", "Flexibility"),
            difficulty = "Beginner",
            durationSeconds = 75,
            instructions = listOf(
                "Start on hands and knees with hips stacked directly over knees.",
                "Walk hands forward while keeping hips high above knees.",
                "Allow chest to melt down toward the floor and rest forehead or chin on mat.",
                "Lengthen spine from tailbone through fingertips."
            ),
            benefits = listOf(
                "Deep stretch for shoulders, upper back, and spine",
                "Releases emotional heaviness stored in the heart and upper back",
                "Soothes tension caused by prolonged desk posture"
            ),
            breathingCue = "Inhale into the space between shoulder blades; exhale surrender heart to mat.",
            modifications = "Place a soft folded blanket under forehead or chest for supported ease.",
            iconType = "restorative"
        )
    )

    val targetPlans = listOf(
        TargetPlan(
            id = "plan_digestion",
            target = "Digestion",
            title = "Digestive Harmony & Gut Flow",
            subtitle = "Gentle twists & abdominal compresses to stimulate gut motility",
            description = "A soothing, targeted sequence designed to relieve bloating, stimulate visceral blood flow, and activate digestive enzymes through gentle spinal twists and seated compressions.",
            level = "Beginner",
            estimatedMinutes = 12,
            poses = listOf(
                PlanPoseItem("childs-pose", 90),
                PlanPoseItem("cat-cow-stretch", 60),
                PlanPoseItem("seated-forward-bend", 90),
                PlanPoseItem("cobra-pose", 45),
                PlanPoseItem("supine-spinal-twist", 90, "Both sides"),
                PlanPoseItem("happy-baby-pose", 75),
                PlanPoseItem("corpse-pose", 90)
            ),
            highlights = listOf("Relieves abdominal bloating", "Stimulates visceral circulation", "Soothes enteric nervous system")
        ),
        TargetPlan(
            id = "plan_mental_focus",
            target = "Mental Focus",
            title = "Clarity & Single-Pointed Focus",
            subtitle = "Grounding balance postures and steady gazes for mental stillness",
            description = "Channel scattered thoughts into centered awareness. Features balance poses and deliberate alignment to anchor the mind in the present moment.",
            level = "All Levels",
            estimatedMinutes = 14,
            poses = listOf(
                PlanPoseItem("childs-pose", 60),
                PlanPoseItem("downward-facing-dog", 75),
                PlanPoseItem("warrior-two", 60, "Right side"),
                PlanPoseItem("warrior-two", 60, "Left side"),
                PlanPoseItem("triangle-pose", 60, "Both sides"),
                PlanPoseItem("tree-pose", 60, "Both sides"),
                PlanPoseItem("bridge-pose", 60),
                PlanPoseItem("corpse-pose", 120)
            ),
            highlights = listOf("Enhances cognitive clarity", "Sharpens steady gaze (Drishti)", "Grounds overactive thoughts")
        ),
        TargetPlan(
            id = "plan_sleep",
            target = "Sleep",
            title = "Deep Rest & Evening Surrender",
            subtitle = "Parasympathetic down-regulation for profound, restorative sleep",
            description = "Slow, grounding postures designed to lower heart rate, ease nocturnal restlessness, and prepare your nervous system for deep sleep.",
            level = "Beginner",
            estimatedMinutes = 15,
            poses = listOf(
                PlanPoseItem("childs-pose", 90),
                PlanPoseItem("puppy-pose", 75),
                PlanPoseItem("seated-forward-bend", 90),
                PlanPoseItem("pigeon-pose", 90, "Both sides"),
                PlanPoseItem("supine-spinal-twist", 90, "Both sides"),
                PlanPoseItem("happy-baby-pose", 75),
                PlanPoseItem("legs-up-the-wall", 120),
                PlanPoseItem("corpse-pose", 150)
            ),
            highlights = listOf("Activates sleep hormones", "Releases physical daytime fatigue", "Calms racing bedtime thoughts")
        ),
        TargetPlan(
            id = "plan_energy",
            target = "Energy",
            title = "Vitality Awakening & Flow",
            subtitle = "Invigorating standing postures and heart openers for natural energy",
            description = "Awaken the body with dynamic breath flow, spinal expansion, and active standing postures that boost circulation without nervous jitters.",
            level = "Intermediate",
            estimatedMinutes = 14,
            poses = listOf(
                PlanPoseItem("cat-cow-stretch", 60),
                PlanPoseItem("downward-facing-dog", 75),
                PlanPoseItem("warrior-two", 60, "Right side"),
                PlanPoseItem("warrior-two", 60, "Left side"),
                PlanPoseItem("triangle-pose", 60, "Both sides"),
                PlanPoseItem("cobra-pose", 60),
                PlanPoseItem("camel-pose", 45),
                PlanPoseItem("bridge-pose", 60),
                PlanPoseItem("corpse-pose", 90)
            ),
            highlights = listOf("Boosts daytime alertness", "Opens lungs for oxygen delivery", "Builds core & spinal strength")
        ),
        TargetPlan(
            id = "plan_stress_relief",
            target = "Stress Relief",
            title = "Tension Release & Calm Mind",
            subtitle = "Deep hip openers and passive folds to melt physical stress",
            description = "Dissolve tension held in the neck, shoulders, and hips with slow, generous holds that trigger nervous system ease and emotional release.",
            level = "All Levels",
            estimatedMinutes = 16,
            poses = listOf(
                PlanPoseItem("childs-pose", 90),
                PlanPoseItem("cat-cow-stretch", 60),
                PlanPoseItem("puppy-pose", 75),
                PlanPoseItem("pigeon-pose", 90, "Both sides"),
                PlanPoseItem("seated-forward-bend", 90),
                PlanPoseItem("supine-spinal-twist", 90, "Both sides"),
                PlanPoseItem("legs-up-the-wall", 120),
                PlanPoseItem("corpse-pose", 120)
            ),
            highlights = listOf("Lowers cortisol response", "Frees chronic shoulder & neck tight spots", "Restores inner emotional equilibrium")
        ),
        TargetPlan(
            id = "plan_flexibility",
            target = "Flexibility",
            title = "Full Body Mobility & Length",
            subtitle = "Comprehensive elongation for hamstrings, hips, and spine",
            description = "A thorough mobility plan focusing on muscle elongation, joint range of motion, and mindful breath extension.",
            level = "All Levels",
            estimatedMinutes = 15,
            poses = listOf(
                PlanPoseItem("downward-facing-dog", 75),
                PlanPoseItem("triangle-pose", 60, "Both sides"),
                PlanPoseItem("seated-forward-bend", 90),
                PlanPoseItem("pigeon-pose", 90, "Both sides"),
                PlanPoseItem("cobra-pose", 45),
                PlanPoseItem("camel-pose", 45),
                PlanPoseItem("puppy-pose", 75),
                PlanPoseItem("corpse-pose", 90)
            ),
            highlights = listOf("Increases hip & hamstring range", "Improves overall postural alignment", "Decompresses vertebral column")
        )
    )

    val initialFavorites = listOf(
        FavoritePose("user_default", "childs-pose"),
        FavoritePose("user_default", "tree-pose"),
        FavoritePose("user_default", "supine-spinal-twist")
    )

    val initialCompletions: List<PracticeCompletion>
        get() {
            val list = mutableListOf<PracticeCompletion>()
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val cal = Calendar.getInstance()

            // Yesterday
            cal.add(Calendar.DAY_OF_YEAR, -1)
            list.add(
                PracticeCompletion(
                    userId = "user_default",
                    completedDate = sdf.format(cal.time),
                    planTarget = "Stress Relief",
                    durationMinutes = 16,
                    timestamp = cal.timeInMillis,
                    notes = "Felt deeply relaxed in Pigeon pose."
                )
            )

            // 2 days ago
            cal.add(Calendar.DAY_OF_YEAR, -1)
            list.add(
                PracticeCompletion(
                    userId = "user_default",
                    completedDate = sdf.format(cal.time),
                    planTarget = "Sleep",
                    durationMinutes = 15,
                    timestamp = cal.timeInMillis,
                    notes = "Fell asleep peacefully right after."
                )
            )

            // 3 days ago
            cal.add(Calendar.DAY_OF_YEAR, -1)
            list.add(
                PracticeCompletion(
                    userId = "user_default",
                    completedDate = sdf.format(cal.time),
                    planTarget = "Flexibility",
                    durationMinutes = 15,
                    timestamp = cal.timeInMillis,
                    notes = "Hamstrings feeling looser."
                )
            )

            return list
        }
}
